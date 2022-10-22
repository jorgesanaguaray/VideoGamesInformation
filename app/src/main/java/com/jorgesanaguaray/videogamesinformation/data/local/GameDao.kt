package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoryEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity

/**
 * Created by Jorge Sanaguaray
 */

@Dao
interface GameDao {


    @Query("SELECT * FROM game_table")
    suspend fun getGame(): GameEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Query("DELETE FROM game_table")
    suspend fun deleteGame()


    @Query("SELECT * FROM category_table")
    suspend fun getCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM category_table")
    suspend fun deleteCategories()


}