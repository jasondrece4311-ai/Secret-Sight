package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.scenes.SceneRegistry
import com.example.ui.components.AboutDialog
import com.example.ui.components.SettingsDialog
import com.example.ui.screens.GameCompleteScreen
import com.example.ui.screens.GameplayScreen
import com.example.ui.screens.LevelSelectScreen
import com.example.ui.screens.TitleScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.GameViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameApp(viewModel = viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val currentScreen = viewModel.currentScreen.value
        val activeState = viewModel.activeLevelState.value
        if (currentScreen == Screen.GAMEPLAY && activeState != null && !activeState.isPaused && !activeState.isCompleted) {
            viewModel.resumeGame()
        } else if (currentScreen == Screen.TITLE) {
            viewModel.audioManager.playAmbient("title")
        }
    }

    override fun onPause() {
        super.onPause()
        if (viewModel.currentScreen.value == Screen.GAMEPLAY) {
            viewModel.pauseGame()
        } else {
            viewModel.audioManager.pauseAmbient()
        }
    }
}

@Composable
fun GameApp(viewModel: GameViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val activeState by viewModel.activeLevelState.collectAsState()
    val progressList by viewModel.progressList.collectAsState()
    val settings by viewModel.settings.collectAsState()

    var showTitleSettings by remember { mutableStateOf(false) }
    var showTitleAbout by remember { mutableStateOf(false) }

    val maxUnlockedLevel = settings?.unlockedLevelMax ?: 1
    val completedCount = progressList.count { it.isCompleted }
    val totalStars = progressList.sumOf { it.stars }
    val totalScore = progressList.sumOf { it.bestScore }
    val allScenes = SceneRegistry.getAllScenes()

    // Back button handling
    BackHandler(enabled = currentScreen != Screen.TITLE) {
        when (currentScreen) {
            Screen.GAMEPLAY -> {
                if (activeState?.isPaused == false) {
                    viewModel.pauseGame()
                } else {
                    viewModel.navigateTo(Screen.LEVEL_SELECT)
                }
            }
            Screen.LEVEL_SELECT -> viewModel.navigateTo(Screen.TITLE)
            Screen.GAME_COMPLETE -> viewModel.navigateTo(Screen.LEVEL_SELECT)
            Screen.TITLE -> { /* Default system back exits */ }
        }
    }

    when (currentScreen) {
        Screen.TITLE -> {
            TitleScreen(
                currentUnlockedLevel = maxUnlockedLevel,
                completedLevelsCount = completedCount,
                totalStars = totalStars,
                onStartGame = { levelId -> viewModel.startLevel(levelId) },
                onOpenLevelSelect = { viewModel.navigateTo(Screen.LEVEL_SELECT) },
                onOpenSettings = { showTitleSettings = true }
            )
        }

        Screen.LEVEL_SELECT -> {
            LevelSelectScreen(
                scenes = allScenes,
                progressList = progressList,
                maxUnlockedLevel = maxUnlockedLevel,
                onSelectLevel = { levelId -> viewModel.startLevel(levelId) },
                onBack = { viewModel.navigateTo(Screen.TITLE) }
            )
        }

        Screen.GAMEPLAY -> {
            activeState?.let { state ->
                val isLastLevel = state.scene.id >= SceneRegistry.getTotalScenesCount()
                GameplayScreen(
                    levelState = state,
                    musicVolume = settings?.musicVolume ?: 0.20f,
                    effectsVolume = settings?.effectsVolume ?: 0.80f,
                    isMusicMuted = settings?.isMusicMuted ?: false,
                    isEffectsMuted = settings?.isEffectsMuted ?: false,
                    isLastLevel = isLastLevel,
                    onTargetTapped = { target -> viewModel.onTargetTapped(target) },
                    onWrongTapped = { offset -> viewModel.onWrongTapped(offset) },
                    onUseHint = { viewModel.useHint() },
                    onPauseClicked = { viewModel.pauseGame() },
                    onResumeClicked = { viewModel.resumeGame() },
                    onRestartLevel = { viewModel.restartCurrentLevel() },
                    onNextLevel = { viewModel.nextLevel() },
                    onOpenLevelSelect = { viewModel.navigateTo(Screen.LEVEL_SELECT) },
                    onMusicVolumeChange = { vol -> viewModel.setMusicVolume(vol) },
                    onEffectsVolumeChange = { vol -> viewModel.setEffectsVolume(vol) },
                    onMusicMuteToggle = { muted -> viewModel.setMusicMuted(muted) },
                    onEffectsMuteToggle = { muted -> viewModel.setEffectsMuted(muted) },
                    onResetProgress = { viewModel.resetAllProgress() }
                )
            }
        }

        Screen.GAME_COMPLETE -> {
            GameCompleteScreen(
                totalLevelsCompleted = completedCount,
                totalObjectsFound = completedCount * 5,
                totalScore = totalScore,
                totalStars = totalStars,
                maxPossibleStars = allScenes.size * 3,
                onPlayAgain = { viewModel.startLevel(1) },
                onLevelSelect = { viewModel.navigateTo(Screen.LEVEL_SELECT) }
            )
        }
    }

    if (showTitleSettings) {
        SettingsDialog(
            musicVolume = settings?.musicVolume ?: 0.20f,
            effectsVolume = settings?.effectsVolume ?: 0.80f,
            isMusicMuted = settings?.isMusicMuted ?: false,
            isEffectsMuted = settings?.isEffectsMuted ?: false,
            onMusicVolumeChange = { vol -> viewModel.setMusicVolume(vol) },
            onEffectsVolumeChange = { vol -> viewModel.setEffectsVolume(vol) },
            onMusicMuteToggle = { muted -> viewModel.setMusicMuted(muted) },
            onEffectsMuteToggle = { muted -> viewModel.setEffectsMuted(muted) },
            onResetProgress = { viewModel.resetAllProgress() },
            onAboutClick = {
                showTitleSettings = false
                showTitleAbout = true
            },
            onDismiss = { showTitleSettings = false }
        )
    }

    if (showTitleAbout) {
        AboutDialog(onDismiss = { showTitleAbout = false })
    }
}
