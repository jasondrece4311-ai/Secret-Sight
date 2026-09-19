package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.example.model.ActiveLevelState
import com.example.model.SceneTarget
import com.example.ui.components.AboutDialog
import com.example.ui.components.FoundItemsBar
import com.example.ui.components.GameHeader
import com.example.ui.components.LevelCompleteDialog
import com.example.ui.components.LevelFailedDialog
import com.example.ui.components.PauseDialog
import com.example.ui.components.SceneViewport
import com.example.ui.components.SettingsDialog

@Composable
fun GameplayScreen(
    levelState: ActiveLevelState,
    musicVolume: Float,
    effectsVolume: Float,
    isMusicMuted: Boolean,
    isEffectsMuted: Boolean,
    isLastLevel: Boolean,
    onTargetTapped: (SceneTarget) -> Unit,
    onWrongTapped: (androidx.compose.ui.geometry.Offset) -> Unit,
    onUseHint: () -> Unit,
    onPauseClicked: () -> Unit,
    onResumeClicked: () -> Unit,
    onRestartLevel: () -> Unit,
    onNextLevel: () -> Unit,
    onOpenLevelSelect: () -> Unit,
    onMusicVolumeChange: (Float) -> Unit,
    onEffectsVolumeChange: (Float) -> Unit,
    onMusicMuteToggle: (Boolean) -> Unit,
    onEffectsMuteToggle: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            GameHeader(
                title = levelState.scene.title,
                levelNumber = levelState.scene.id,
                elapsedSeconds = levelState.elapsedSeconds,
                mistakes = levelState.mistakes,
                maxMistakes = levelState.scene.maxMistakes,
                hintsRemaining = levelState.hintsRemaining,
                onPauseClicked = onPauseClicked,
                onHintClicked = onUseHint
            )
        },
        bottomBar = {
            FoundItemsBar(
                targets = levelState.targets
            )
        },
        modifier = modifier
            .fillMaxSize()
            .testTag("gameplay_screen")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF1B2620))
        ) {
            SceneViewport(
                levelState = levelState,
                onTargetTapped = onTargetTapped,
                onWrongTapped = onWrongTapped,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // Pause Dialog
    if (levelState.isPaused && !showSettingsDialog && !showAboutDialog) {
        PauseDialog(
            onResume = onResumeClicked,
            onRestart = onRestartLevel,
            onSettings = { showSettingsDialog = true },
            onLevelSelect = onOpenLevelSelect
        )
    }

    // Level Complete Dialog
    if (levelState.isCompleted) {
        levelState.result?.let { result ->
            LevelCompleteDialog(
                result = result,
                isLastLevel = isLastLevel,
                onContinue = onNextLevel,
                onReplay = onRestartLevel,
                onLevelSelect = onOpenLevelSelect
            )
        }
    }

    // Level Failed Dialog
    if (levelState.isFailed) {
        LevelFailedDialog(
            foundCount = levelState.targets.count { it.isDiscovered },
            mistakes = levelState.mistakes,
            onTryAgain = onRestartLevel,
            onLevelSelect = onOpenLevelSelect
        )
    }

    // Settings Dialog
    if (showSettingsDialog) {
        SettingsDialog(
            musicVolume = musicVolume,
            effectsVolume = effectsVolume,
            isMusicMuted = isMusicMuted,
            isEffectsMuted = isEffectsMuted,
            onMusicVolumeChange = onMusicVolumeChange,
            onEffectsVolumeChange = onEffectsVolumeChange,
            onMusicMuteToggle = onMusicMuteToggle,
            onEffectsMuteToggle = onEffectsMuteToggle,
            onResetProgress = onResetProgress,
            onAboutClick = {
                showSettingsDialog = false
                showAboutDialog = true
            },
            onDismiss = { showSettingsDialog = false }
        )
    }

    // About Dialog
    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}
