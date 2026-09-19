package com.example.model

import androidx.compose.ui.geometry.Offset

enum class GameScreenState {
    TITLE,
    LEVEL_SELECT,
    PLAYING,
    LEVEL_COMPLETED,
    LEVEL_FAILED,
    GAME_COMPLETED,
    SETTINGS,
    ABOUT
}

data class ActiveLevelState(
    val scene: SceneDefinition,
    val targets: List<SceneTarget>,
    val mistakes: Int = 0,
    val elapsedSeconds: Int = 0,
    val hintsRemaining: Int = 3,
    val hintsUsed: Int = 0,
    val activeHintTargetId: String? = null,
    val activeHintLocation: Offset? = null,
    val activeHintTriggerTime: Long = 0L,
    val recentFoundTarget: SceneTarget? = null,
    val lastWrongTapScene: Offset? = null,
    val isPaused: Boolean = false,
    val isCompleted: Boolean = false,
    val isFailed: Boolean = false,
    val result: LevelResult? = null
) {
    val foundCount: Int get() = targets.count { it.isDiscovered }
    val remainingCount: Int get() = targets.count { !it.isDiscovered }
    val isAllFound: Boolean get() = foundCount == 5
    val mistakesRemaining: Int get() = (scene.maxMistakes - mistakes).coerceAtLeast(0)
}
