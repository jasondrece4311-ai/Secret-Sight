package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_progress")
data class LevelProgressEntity(
    @PrimaryKey val levelId: Int,
    val isCompleted: Boolean,
    val stars: Int,
    val bestScore: Int,
    val bestTimeSeconds: Int,
    val fewestMistakes: Int
)
