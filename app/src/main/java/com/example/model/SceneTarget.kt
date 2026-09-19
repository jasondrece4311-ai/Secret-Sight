package com.example.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

data class SceneRect(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
    val centerX: Float get() = (left + right) / 2f
    val centerY: Float get() = (top + bottom) / 2f

    fun contains(x: Float, y: Float): Boolean {
        return x in left..right && y in top..bottom
    }

    fun toComposeRect(): Rect = Rect(left, top, right, bottom)
}

data class SceneTarget(
    val id: String,
    val name: String,
    val clue: String,
    val hitBox: SceneRect,
    val hintLocation: Offset = Offset(hitBox.centerX, hitBox.centerY),
    val highlightRadius: Float = maxOf(hitBox.width, hitBox.height) / 2f + 16f,
    val isDiscovered: Boolean = false
) {
    fun isHit(sceneX: Float, sceneY: Float): Boolean {
        return hitBox.contains(sceneX, sceneY)
    }
}
