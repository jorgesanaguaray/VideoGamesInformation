package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Jorge Sanaguaray
 */

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("DELETE FROM game_table")
    suspend fun deleteGames()

    @Query("SELECT * FROM game_table ORDER BY title DESC")
    suspend fun getGames(): List<GameEntity>

}