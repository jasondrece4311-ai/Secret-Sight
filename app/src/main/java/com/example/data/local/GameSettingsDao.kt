package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameSettingsDao {
    @Query("SELECT * FROM game_settings WHERE id = 1 LIMIT 1")
    fun getSettingsFlow(): Flow<GameSettingsEntity?>

    @Query("SELECT * FROM game_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettings(): GameSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: GameSettingsEntity)

    @Query("UPDATE game_settings SET unlockedLevelMax = :maxLevel WHERE id = 1")
    suspend fun updateMaxUnlockedLevel(maxLevel: Int)

    @Query("DELETE FROM game_settings")
    suspend fun clearSettings()
}
