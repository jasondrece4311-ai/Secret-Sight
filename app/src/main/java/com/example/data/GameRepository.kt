package com.example.data

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.GameSettingsEntity
import com.example.data.local.LevelProgressEntity
import com.example.model.LevelResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class GameRepository(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val progressDao = database.levelProgressDao()
    private val settingsDao = database.gameSettingsDao()

    val allProgress: Flow<List<LevelProgressEntity>> = progressDao.getAllProgressFlow()
        .flowOn(Dispatchers.IO)

    val settingsFlow: Flow<GameSettingsEntity?> = settingsDao.getSettingsFlow()
        .flowOn(Dispatchers.IO)

    suspend fun getSettings(): GameSettingsEntity = withContext(Dispatchers.IO) {
        try {
            settingsDao.getSettings() ?: GameSettingsEntity().also {
                settingsDao.saveSettings(it)
            }
        } catch (e: Exception) {
            GameSettingsEntity()
        }
    }

    suspend fun saveSettings(settings: GameSettingsEntity) = withContext(Dispatchers.IO) {
        try {
            settingsDao.saveSettings(settings)
        } catch (_: Exception) {}
    }

    suspend fun recordLevelCompletion(result: LevelResult): Boolean = withContext(Dispatchers.IO) {
        try {
            val existing = progressDao.getProgressForLevel(result.levelId)
            val updated = if (existing != null) {
                existing.copy(
                    isCompleted = true,
                    stars = maxOf(existing.stars, result.stars),
                    bestScore = maxOf(existing.bestScore, result.score),
                    bestTimeSeconds = if (existing.bestTimeSeconds == 0) result.timeSeconds else minOf(existing.bestTimeSeconds, result.timeSeconds),
                    fewestMistakes = minOf(existing.fewestMistakes, result.mistakes)
                )
            } else {
                LevelProgressEntity(
                    levelId = result.levelId,
                    isCompleted = true,
                    stars = result.stars,
                    bestScore = result.score,
                    bestTimeSeconds = result.timeSeconds,
                    fewestMistakes = result.mistakes
                )
            }
            progressDao.saveProgress(updated)

            val nextLevel = result.levelId + 1
            val currentSettings = getSettings()
            if (nextLevel > currentSettings.unlockedLevelMax && nextLevel <= 20) {
                settingsDao.updateMaxUnlockedLevel(nextLevel)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun resetAllProgress() = withContext(Dispatchers.IO) {
        try {
            progressDao.resetAllProgress()
            val defaultSettings = GameSettingsEntity(
                id = 1,
                musicVolume = 0.20f,
                effectsVolume = 0.80f,
                isMusicMuted = false,
                isEffectsMuted = false,
                unlockedLevelMax = 1
            )
            settingsDao.saveSettings(defaultSettings)
            true
        } catch (e: Exception) {
            false
        }
    }
}
