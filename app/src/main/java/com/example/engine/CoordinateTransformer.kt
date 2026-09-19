package com.example.engine

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.example.model.SceneRect
import kotlin.math.min

object SceneConstants {
    const val SCENE_WIDTH = 1200f
    const val SCENE_HEIGHT = 900f
    const val MIN_ZOOM = 1.0f
    const val MAX_ZOOM = 3.0f
    const val DEFAULT_ZOOM = 1.0f
    const val DOUBLE_TAP_ZOOM = 2.4f
}

data class ViewportState(
    val scale: Float = SceneConstants.DEFAULT_ZOOM,
    val panOffset: Offset = Offset.Zero
)

class CoordinateTransformer(
    val viewWidth: Float,
    val viewHeight: Float,
    val sceneWidth: Float = SceneConstants.SCENE_WIDTH,
    val sceneHeight: Float = SceneConstants.SCENE_HEIGHT
) {
    val baseScale: Float = if (viewWidth > 0f && viewHeight > 0f) {
        min(viewWidth / sceneWidth, viewHeight / sceneHeight)
    } else {
        1.0f
    }

    val baseContentWidth: Float = sceneWidth * baseScale
    val baseContentHeight: Float = sceneHeight * baseScale
    val baseOriginX: Float = (viewWidth - baseContentWidth) / 2f
    val baseOriginY: Float = (viewHeight - baseContentHeight) / 2f

    fun sceneToScreen(scenePoint: Offset, viewport: ViewportState): Offset {
        val totalScale = baseScale * viewport.scale
        val relX = scenePoint.x - (sceneWidth / 2f)
        val relY = scenePoint.y - (sceneHeight / 2f)
        val screenX = (viewWidth / 2f) + viewport.panOffset.x + (relX * totalScale)
        val screenY = (viewHeight / 2f) + viewport.panOffset.y + (relY * totalScale)
        return Offset(screenX, screenY)
    }

    fun screenToScene(screenPoint: Offset, viewport: ViewportState): Offset {
        val totalScale = baseScale * viewport.scale
        if (totalScale <= 0f) return Offset.Zero
        val relX = (screenPoint.x - (viewWidth / 2f) - viewport.panOffset.x) / totalScale
        val relY = (screenPoint.y - (viewHeight / 2f) - viewport.panOffset.y) / totalScale
        val sceneX = relX + (sceneWidth / 2f)
        val sceneY = relY + (sceneHeight / 2f)
        return Offset(sceneX, sceneY)
    }

    fun sceneRectToScreen(sceneRect: SceneRect, viewport: ViewportState): Rect {
        val topLeft = sceneToScreen(Offset(sceneRect.left, sceneRect.top), viewport)
        val bottomRight = sceneToScreen(Offset(sceneRect.right, sceneRect.bottom), viewport)
        return Rect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y)
    }

    fun isScreenPointInScene(screenPoint: Offset, viewport: ViewportState): Boolean {
        val scenePoint = screenToScene(screenPoint, viewport)
        return scenePoint.x in 0f..sceneWidth && scenePoint.y in 0f..sceneHeight
    }

    fun clampPan(pan: Offset, scale: Float, extraMargin: Float = 60f): Offset {
        val scaledWidth = baseContentWidth * scale
        val scaledHeight = baseContentHeight * scale

        val maxPanX = if (scaledWidth > viewWidth) {
            ((scaledWidth - viewWidth) / 2f) + extraMargin
        } else {
            extraMargin * 0.5f
        }

        val maxPanY = if (scaledHeight > viewHeight) {
            ((scaledHeight - viewHeight) / 2f) + extraMargin
        } else {
            extraMargin * 0.5f
        }

        val clampedX = pan.x.coerceIn(-maxPanX, maxPanX)
        val clampedY = pan.y.coerceIn(-maxPanY, maxPanY)
        return Offset(clampedX, clampedY)
    }

    fun calculateDoubleTapPan(
        tapScreen: Offset,
        targetScale: Float,
        currentViewport: ViewportState
    ): Offset {
        if (targetScale <= 1.05f) {
            return Offset.Zero
        }
        val tapScene = screenToScene(tapScreen, currentViewport)
        val relX = tapScene.x - (sceneWidth / 2f)
        val relY = tapScene.y - (sceneHeight / 2f)
        val newTotalScale = baseScale * targetScale
        val desiredPanX = -(relX * newTotalScale)
        val desiredPanY = -(relY * newTotalScale)
        return clampPan(Offset(desiredPanX, desiredPanY), targetScale)
    }
}
