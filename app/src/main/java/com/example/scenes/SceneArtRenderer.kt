package com.example.scenes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

/**
 * Renders the real photographic artwork for the current Secret Sight scene.
 *
 * The game scene coordinate system remains 1200 x 900.
 * Existing target hitboxes therefore continue to use the same coordinate space.
 *
 * IMPORTANT:
 * The photograph is rendered as the actual scene artwork.
 * No procedural/cartoon replacement artwork is generated here.
 */
object SceneArtRenderer {

    private const val SCENE_WIDTH = 1200
    private const val SCENE_HEIGHT = 900

    fun render(
        drawScope: DrawScope,
        sceneId: Int,
        discoveredTargetIds: Set<String>,
        sceneBitmap: ImageBitmap?
    ) {
        with(drawScope) {

            if (sceneBitmap != null) {
                drawImage(
                    image = sceneBitmap,
                    dstOffset = IntOffset.Zero,
                    dstSize = IntSize(
                        SCENE_WIDTH,
                        SCENE_HEIGHT
                    )
                )
            } else {
                // This is an actual missing-resource state.
                // We deliberately do NOT generate fake artwork.
                drawRect(
                    color = Color(0xFF101418),
                    size = size
                )
            }

            /*
             * Discovery feedback is drawn above the photograph.
             *
             * The photograph itself remains untouched.
             */
            val scene = SceneRegistry.getSceneById(sceneId)

            scene?.targets?.forEach { target ->

                if (target.id in discoveredTargetIds) {

                    val location = target.hintLocation

                    drawCircle(
                        color = Color(0x3348C774),
                        radius = 48f,
                        center = location
                    )

                    drawCircle(
                        color = Color(0xFF2ECC71),
                        radius = 38f,
                        center = location,
                        style = Stroke(width = 3.5f)
                    )

                    drawCircle(
                        color = Color(0xFF27AE60),
                        radius = 14f,
                        center = Offset(
                            location.x + 24f,
                            location.y - 24f
                        )
                    )
                }
            }
        }
    }
}
