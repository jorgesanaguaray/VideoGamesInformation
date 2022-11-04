package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgesanaguaray.videogamesinformation.data.local.entities.*

/**
 * Created by Jorge Sanaguaray
 */

@Dao
interface GameDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Query("DELETE FROM game_table")
    suspend fun deleteGame()

    @Query("SELECT * FROM game_table")
    suspend fun getGame(): GameEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryGames(categoryGames: List<CategoryGameEntity>)

    @Query("DELETE FROM category_game_table")
    suspend fun deleteCategoryGames()

    @Query("SELECT * FROM category_game_table")
    suspend fun getCategoryGames(): List<CategoryGameEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatformGames(platformGames: List<PlatformGameEntity>)

    @Query("DELETE FROM platform_game_table")
    suspend fun deletePlatformGames()

    @Query("SELECT * FROM platform_game_table")
    suspend fun getPlatformGames(): List<PlatformGameEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GamesEntity>)

    @Query("DELETE FROM games_table")
    suspend fun deleteGames()

    @Query("SELECT * FROM games_table")
    suspend fun getGames(): List<GamesEntity>


    @Insert
    suspend fun insertFavoriteGame(favoriteGame: FavoriteGameEntity)

    @Query("DELETE FROM favorite_game_table WHERE id = :id")
    suspend fun deleteFavoriteGameById(id: Int)

    @Query("DELETE FROM favorite_game_table")
    suspend fun deleteFavoriteGames()

    @Query("SELECT * FROM favorite_game_table WHERE id = :id")
    suspend fun getFavoriteGameById(id: Int): FavoriteGameEntity

    @Query("SELECT * FROM favorite_game_table")
    suspend fun getFavoriteGames(): List<FavoriteGameEntity>


}