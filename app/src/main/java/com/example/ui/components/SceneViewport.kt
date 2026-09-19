package com.example.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.engine.CoordinateTransformer
import com.example.engine.SceneConstants
import com.example.engine.ViewportState
import com.example.model.ActiveLevelState
import com.example.model.SceneTarget
import com.example.scenes.SceneArtRenderer
import kotlin.math.sqrt

@Composable
fun SceneViewport(
    levelState: ActiveLevelState,
    onTargetTapped: (SceneTarget) -> Unit,
    onWrongTapped: (Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val forestBitmap = remember(context) {
        try {
            BitmapFactory.decodeResource(context.resources, R.drawable.img_enchanted_forest)?.asImageBitmap()
        } catch (e: Throwable) {
            null
        }
    }

    var rawScale by remember { mutableFloatStateOf(SceneConstants.DEFAULT_ZOOM) }
    var rawPan by remember { mutableStateOf(Offset.Zero) }

    // Smooth animation on double-tap zoom changes
    val animatedScale by animateFloatAsState(
        targetValue = rawScale,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "scale_anim"
    )
    val animatedPan by animateOffsetAsState(
        targetValue = rawPan,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "pan_anim"
    )

    val currentViewport = ViewportState(scale = animatedScale, panOffset = animatedPan)

    // Pulse animation for active hint
    val infiniteTransition = rememberInfiniteTransition(label = "hint_pulse")
    val hintPulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hint_alpha"
    )
    val hintPulseRadius by infiniteTransition.animateFloat(
        initialValue = 24f,
        targetValue = 75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hint_radius"
    )

    val density = LocalDensity.current
    val touchSlopPx = with(density) { 14.dp.toPx() }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .testTag("scene_viewport")
    ) {
        val viewWidth = constraints.maxWidth.toFloat()
        val viewHeight = constraints.maxHeight.toFloat()

        val transformer = remember(viewWidth, viewHeight) {
            CoordinateTransformer(viewWidth = viewWidth, viewHeight = viewHeight)
        }

        var lastTapTime by remember { mutableLongStateOf(0L) }
        var lastTapScreen by remember { mutableStateOf(Offset.Zero) }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(levelState.scene.id, levelState.isPaused, levelState.isCompleted) {
                    if (levelState.isPaused || levelState.isCompleted || levelState.isFailed) return@pointerInput

                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        var isTransforming = false
                        val startPos = down.position
                        val downTime = System.currentTimeMillis()

                        do {
                            val event = awaitPointerEvent()
                            val pointerCount = event.changes.size

                            if (pointerCount >= 2) {
                                isTransforming = true
                                val zoomChange = event.calculateZoom()
                                val panChange = event.calculatePan()

                                val newScale = (rawScale * zoomChange).coerceIn(
                                    SceneConstants.MIN_ZOOM,
                                    SceneConstants.MAX_ZOOM
                                )
                                rawScale = newScale
                                val newPan = transformer.clampPan(rawPan + panChange, newScale)
                                rawPan = newPan
                                event.changes.forEach { it.consume() }
                            } else if (pointerCount == 1) {
                                val change = event.changes.first()
                                val totalDrag = change.position - startPos
                                val dragDistance = sqrt(totalDrag.x * totalDrag.x + totalDrag.y * totalDrag.y)

                                if (dragDistance > touchSlopPx) {
                                    isTransforming = true
                                    if (rawScale > 1.05f) {
                                        val panDelta = change.positionChange()
                                        rawPan = transformer.clampPan(rawPan + panDelta, rawScale)
                                    }
                                    change.consume()
                                }
                            }
                        } while (event.changes.any { it.pressed })

                        // Finger released
                        val upTime = System.currentTimeMillis()
                        val duration = upTime - downTime

                        if (!isTransforming && duration < 320) {
                            val tapPos = down.position
                            val timeSinceLastTap = upTime - lastTapTime
                            val tapDist = sqrt(
                                (tapPos.x - lastTapScreen.x) * (tapPos.x - lastTapScreen.x) +
                                        (tapPos.y - lastTapScreen.y) * (tapPos.y - lastTapScreen.y)
                            )

                            if (timeSinceLastTap < 350 && tapDist < 60f) {
                                // Double-tap detected: zoom toggle
                                if (rawScale > 1.25f) {
                                    rawScale = SceneConstants.MIN_ZOOM
                                    rawPan = Offset.Zero
                                } else {
                                    rawScale = SceneConstants.DOUBLE_TAP_ZOOM
                                    rawPan = transformer.calculateDoubleTapPan(
                                        tapScreen = tapPos,
                                        targetScale = SceneConstants.DOUBLE_TAP_ZOOM,
                                        currentViewport = currentViewport
                                    )
                                }
                                lastTapTime = 0L
                            } else {
                                // Single tap detected: target hit test
                                lastTapTime = upTime
                                lastTapScreen = tapPos

                                if (transformer.isScreenPointInScene(tapPos, currentViewport)) {
                                    val sceneCoord = transformer.screenToScene(tapPos, currentViewport)
                                    val hitTarget = levelState.targets.find { target ->
                                        !target.isDiscovered && target.isHit(sceneCoord.x, sceneCoord.y)
                                    }

                                    if (hitTarget != null) {
                                        onTargetTapped(hitTarget)
                                    } else {
                                        onWrongTapped(sceneCoord)
                                    }
                                }
                            }
                        }
                    }
                }
        ) {
            // Transform canvas to match virtual scene coordinate space
            translate(
                left = (viewWidth / 2f) + currentViewport.panOffset.x,
                top = (viewHeight / 2f) + currentViewport.panOffset.y
            ) {
                val totalScale = transformer.baseScale * currentViewport.scale
                scale(scaleX = totalScale, scaleY = totalScale, pivot = Offset.Zero) {
                    translate(
                        left = -(SceneConstants.SCENE_WIDTH / 2f),
                        top = -(SceneConstants.SCENE_HEIGHT / 2f)
                    ) {
                        // Render full rich artistic scene
                        val discoveredIds = levelState.targets.filter { it.isDiscovered }.map { it.id }.toSet()
                        SceneArtRenderer.render(
                            drawScope = this,
                            sceneId = levelState.scene.id,
                            discoveredTargetIds = discoveredIds,
                            forestBitmap = forestBitmap
                        )

                        // Render active hint glow / pulse if triggered
                        levelState.activeHintLocation?.let { hintLoc ->
                            drawCircle(
                                color = Color(0xFFFFD700).copy(alpha = hintPulseAlpha * 0.4f),
                                radius = hintPulseRadius + 20f,
                                center = hintLoc
                            )
                            drawCircle(
                                color = Color(0xFFF39C12).copy(alpha = hintPulseAlpha),
                                radius = hintPulseRadius,
                                center = hintLoc,
                                style = Stroke(width = 4f)
                            )
                            drawCircle(
                                color = Color(0xFFFFF9C4),
                                radius = 8f,
                                center = hintLoc
                            )
                        }

                        // Render subtle wrong-tap indicator if recent
                        levelState.lastWrongTapScene?.let { wrongLoc ->
                            drawCircle(
                                color = Color(0x66E74C3C),
                                radius = 24f,
                                center = wrongLoc
                            )
                            drawCircle(
                                color = Color(0xFFE74C3C),
                                radius = 18f,
                                center = wrongLoc,
                                style = Stroke(width = 2.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}
