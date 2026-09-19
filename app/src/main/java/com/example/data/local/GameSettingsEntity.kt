package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_settings")
data class GameSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val musicVolume: Float = 0.20f,
    val effectsVolume: Float = 0.80f,
    val isMusicMuted: Boolean = false,
    val isEffectsMuted: Boolean = false,
    val unlockedLevelMax: Int = 1
)
