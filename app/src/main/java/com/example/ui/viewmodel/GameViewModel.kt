package com.example.ui.viewmodel

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.GameAudioManager
import com.example.audio.SoundEffectType
import com.example.data.GameRepository
import com.example.data.local.GameSettingsEntity
import com.example.data.local.LevelProgressEntity
import com.example.model.ActiveLevelState
import com.example.model.SceneTarget
import com.example.model.ScoreCalculator
import com.example.scenes.SceneRegistry
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class Screen {
    TITLE,
    GAMEPLAY,
    LEVEL_SELECT,
    GAME_COMPLETE
}

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application)
    val audioManager = GameAudioManager(application)

    val progressList: StateFlow<List<LevelProgressEntity>> = repository.allProgress
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val settings: StateFlow<GameSettingsEntity?> = repository.settingsFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _currentScreen = MutableStateFlow(Screen.TITLE)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _activeLevelState = MutableStateFlow<ActiveLevelState?>(null)
    val activeLevelState: StateFlow<ActiveLevelState?> = _activeLevelState.asStateFlow()

    private var timerJob: Job? = null
    private var hintJob: Job? = null

    init {
        // Sync audio manager with saved settings once loaded
        viewModelScope.launch {
            repository.settingsFlow.collect { saved ->
                if (saved != null) {
                    audioManager.setMusicVolume(saved.musicVolume)
                    audioManager.setEffectsVolume(saved.effectsVolume)
                    audioManager.setMusicMuted(saved.isMusicMuted)
                    audioManager.setEffectsMuted(saved.isEffectsMuted)
                }
            }
        }
    }

    fun startLevel(levelId: Int) {
        val scene = SceneRegistry.getSceneById(levelId) ?: return

        timerJob?.cancel()
        hintJob?.cancel()

        _activeLevelState.value = ActiveLevelState(
            scene = scene,
            targets = scene.targets.map { it.copy(isDiscovered = false) },
            elapsedSeconds = 0,
            mistakes = 0,
            hintsRemaining = 3,
            isPaused = false,
            isCompleted = false,
            isFailed = false,
            activeHintLocation = null,
            result = null
        )

        _currentScreen.value = Screen.GAMEPLAY
        audioManager.playAmbient(scene.ambientAudioKey)
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _activeLevelState.update { current ->
                    if (current != null && !current.isPaused && !current.isCompleted && !current.isFailed) {
                        current.copy(elapsedSeconds = current.elapsedSeconds + 1)
                    } else {
                        current
                    }
                }
            }
        }
    }

    fun onTargetTapped(target: SceneTarget) {
        val state = _activeLevelState.value ?: return
        if (state.isPaused || state.isCompleted || state.isFailed) return

        val alreadyDiscovered = state.targets.find { it.id == target.id }?.isDiscovered == true
        if (alreadyDiscovered) return

        audioManager.playEffect(SoundEffectType.FOUND)

        val updatedTargets = state.targets.map {
            if (it.id == target.id) it.copy(isDiscovered = true) else it
        }

        val allFound = updatedTargets.all { it.isDiscovered }

        if (allFound) {
            timerJob?.cancel()
            val result = ScoreCalculator.calculate(
                levelId = state.scene.id,
                totalFound = updatedTargets.size,
                mistakes = state.mistakes,
                maxMistakes = state.scene.maxMistakes,
                timeSeconds = state.elapsedSeconds,
                hintsUsed = 3 - state.hintsRemaining,
                starThreshold = state.scene.starThreshold
            )

            audioManager.playEffect(SoundEffectType.LEVEL_COMPLETE)

            _activeLevelState.value = state.copy(
                targets = updatedTargets,
                isCompleted = true,
                result = result,
                activeHintLocation = null
            )

            viewModelScope.launch {
                repository.recordLevelCompletion(result)
            }
        } else {
            _activeLevelState.value = state.copy(
                targets = updatedTargets,
                activeHintLocation = if (state.activeHintLocation == target.hintLocation) null else state.activeHintLocation
            )
        }
    }

    fun onWrongTapped(sceneOffset: Offset) {
        val state = _activeLevelState.value ?: return
        if (state.isPaused || state.isCompleted || state.isFailed) return

        audioManager.playEffect(SoundEffectType.MISTAKE)

        val newMistakes = state.mistakes + 1
        val isFailed = newMistakes >= state.scene.maxMistakes

        if (isFailed) {
            timerJob?.cancel()
        }

        _activeLevelState.value = state.copy(
            mistakes = newMistakes,
            isFailed = isFailed,
            lastWrongTapScene = sceneOffset
        )
    }

    fun useHint() {
        val state = _activeLevelState.value ?: return
        if (state.isPaused || state.isCompleted || state.isFailed || state.hintsRemaining <= 0) return
        if (state.activeHintLocation != null) return

        val undiscovered = state.targets.filter { !it.isDiscovered }
        if (undiscovered.isEmpty()) return

        val hintTarget = undiscovered.random()
        audioManager.playEffect(SoundEffectType.HINT)

        _activeLevelState.value = state.copy(
            hintsRemaining = state.hintsRemaining - 1,
            activeHintLocation = hintTarget.hintLocation
        )

        // Clear pulse after 4.5 seconds
        hintJob?.cancel()
        hintJob = viewModelScope.launch {
            delay(4500)
            _activeLevelState.update { current ->
                current?.copy(activeHintLocation = null)
            }
        }
    }

    fun pauseGame() {
        _activeLevelState.update { it?.copy(isPaused = true) }
        audioManager.pauseAmbient()
    }

    fun resumeGame() {
        _activeLevelState.update { it?.copy(isPaused = false) }
        audioManager.resumeAmbient()
    }

    fun restartCurrentLevel() {
        val currentId = _activeLevelState.value?.scene?.id ?: 1
        startLevel(currentId)
    }

    fun nextLevel() {
        val currentId = _activeLevelState.value?.scene?.id ?: 1
        val totalScenes = SceneRegistry.getTotalScenesCount()

        if (currentId >= totalScenes) {
            _currentScreen.value = Screen.GAME_COMPLETE
        } else {
            startLevel(currentId + 1)
        }
    }

    fun navigateTo(screen: Screen) {
        if (screen == Screen.TITLE || screen == Screen.LEVEL_SELECT || screen == Screen.GAME_COMPLETE) {
            timerJob?.cancel()
            if (screen == Screen.TITLE) {
                audioManager.playAmbient("title")
            }
        }
        _currentScreen.value = screen
    }

    fun setMusicVolume(volume: Float) {
        audioManager.setMusicVolume(volume)
        viewModelScope.launch {
            val current = repository.getSettings()
            repository.saveSettings(current.copy(musicVolume = volume))
        }
    }

    fun setEffectsVolume(volume: Float) {
        audioManager.setEffectsVolume(volume)
        viewModelScope.launch {
            val current = repository.getSettings()
            repository.saveSettings(current.copy(effectsVolume = volume))
        }
    }

    fun setMusicMuted(muted: Boolean) {
        audioManager.setMusicMuted(muted)
        viewModelScope.launch {
            val current = repository.getSettings()
            repository.saveSettings(current.copy(isMusicMuted = muted))
        }
    }

    fun setEffectsMuted(muted: Boolean) {
        audioManager.setEffectsMuted(muted)
        viewModelScope.launch {
            val current = repository.getSettings()
            repository.saveSettings(current.copy(isEffectsMuted = muted))
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            _activeLevelState.value = null
            _currentScreen.value = Screen.TITLE
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        hintJob?.cancel()
        audioManager.release()
    }
}
