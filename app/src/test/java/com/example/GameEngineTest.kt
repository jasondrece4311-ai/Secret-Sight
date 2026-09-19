package com.example

import androidx.compose.ui.geometry.Offset
import com.example.engine.CoordinateTransformer
import com.example.engine.SceneConstants
import com.example.engine.ViewportState
import com.example.model.SceneDefinition
import com.example.model.SceneRect
import com.example.model.SceneTarget
import com.example.model.ScoreCalculator
import com.example.model.StarThreshold
import com.example.scenes.SceneData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GameEngineTest {

    @Test
    fun testTargetHitDetection() {
        val target = SceneTarget(
            id = "test_item",
            name = "Test Item",
            clue = "Look in the center",
            hitBox = SceneRect(left = 100f, top = 100f, right = 200f, bottom = 200f),
            hintLocation = Offset(150f, 150f)
        )

        // Inside
        assertTrue(target.isHit(150f, 150f))
        assertTrue(target.isHit(100f, 100f))
        assertTrue(target.isHit(200f, 200f))

        // Outside
        assertFalse(target.isHit(99f, 150f))
        assertFalse(target.isHit(201f, 150f))
        assertFalse(target.isHit(150f, 99f))
        assertFalse(target.isHit(150f, 201f))
    }

    @Test
    fun testCoordinateTransformerAtDefaultScale() {
        val viewWidth = 1080f
        val viewHeight = 2400f
        val transformer = CoordinateTransformer(viewWidth = viewWidth, viewHeight = viewHeight)
        val viewport = ViewportState(scale = 1.0f, panOffset = Offset.Zero)

        // The center of the screen should map directly to the center of the scene (600, 450)
        val screenCenter = Offset(viewWidth / 2f, viewHeight / 2f)
        val sceneCoord = transformer.screenToScene(screenCenter, viewport)

        assertEquals(SceneConstants.SCENE_WIDTH / 2f, sceneCoord.x, 1.0f)
        assertEquals(SceneConstants.SCENE_HEIGHT / 2f, sceneCoord.y, 1.0f)

        // Round-trip mapping: scene -> screen -> scene
        val originalScenePoint = Offset(300f, 400f)
        val screenPoint = transformer.sceneToScreen(originalScenePoint, viewport)
        val mappedBack = transformer.screenToScene(screenPoint, viewport)

        assertEquals(originalScenePoint.x, mappedBack.x, 0.5f)
        assertEquals(originalScenePoint.y, mappedBack.y, 0.5f)
    }

    @Test
    fun testCoordinateTransformerWithZoomAndPan() {
        val viewWidth = 1080f
        val viewHeight = 2400f
        val transformer = CoordinateTransformer(viewWidth = viewWidth, viewHeight = viewHeight)
        val viewport = ViewportState(scale = 2.5f, panOffset = Offset(120f, -80f))

        val originalScenePoint = Offset(850f, 250f)
        val screenPoint = transformer.sceneToScreen(originalScenePoint, viewport)
        val mappedBack = transformer.screenToScene(screenPoint, viewport)

        assertEquals(originalScenePoint.x, mappedBack.x, 0.5f)
        assertEquals(originalScenePoint.y, mappedBack.y, 0.5f)
    }

    @Test
    fun testScoreAndStarCalculation() {
        val threshold = StarThreshold(
            maxMistakesFor3Stars = 2,
            maxSecondsFor3Stars = 180,
            maxMistakesFor2Stars = 5
        )

        // 3 Stars: 0 mistakes, quick time, 0 hints
        val threeStarResult = ScoreCalculator.calculate(
            levelId = 1,
            totalFound = 5,
            mistakes = 0,
            maxMistakes = 10,
            timeSeconds = 45,
            hintsUsed = 0,
            starThreshold = threshold
        )
        assertEquals(3, threeStarResult.stars)
        assertTrue(threeStarResult.score > 5000)

        // 2 Stars: 4 mistakes (more than 2, but <= 5)
        val twoStarResult = ScoreCalculator.calculate(
            levelId = 1,
            totalFound = 5,
            mistakes = 4,
            maxMistakes = 10,
            timeSeconds = 90,
            hintsUsed = 0,
            starThreshold = threshold
        )
        assertEquals(2, twoStarResult.stars)

        // 1 Star: 6 mistakes
        val oneStarResult = ScoreCalculator.calculate(
            levelId = 1,
            totalFound = 5,
            mistakes = 6,
            maxMistakes = 10,
            timeSeconds = 120,
            hintsUsed = 0,
            starThreshold = threshold
        )
        assertEquals(1, oneStarResult.stars)
    }

    @Test
    fun testSceneDefinitionsIntegrity() {
        val scenes = com.example.scenes.SceneRegistry.getAllScenes()
        assertEquals(20, scenes.size)

        val allIds = mutableSetOf<Int>()
        val allTargetIds = mutableSetOf<String>()

        for (scene in scenes) {
            assertTrue(allIds.add(scene.id))
            assertEquals(5, scene.targets.size)
            assertNotNull(scene.title)
            assertNotNull(scene.ambientAudioKey)

            for (target in scene.targets) {
                assertTrue("Duplicate target id ${target.id}", allTargetIds.add(target.id))
                assertNotNull(target.id)
                assertNotNull(target.name)
                assertNotNull(target.clue)
                assertTrue(target.hitBox.right > target.hitBox.left)
                assertTrue(target.hitBox.bottom > target.hitBox.top)
            }
        }
    }
}
