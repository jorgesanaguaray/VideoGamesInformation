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


    @Query("SELECT * FROM game_table")
    suspend fun getGame(): GameEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Query("DELETE FROM game_table")
    suspend fun deleteGame()


    @Query("SELECT * FROM categories_table")
    suspend fun getCategories(): List<CategoriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoriesEntity>)

    @Query("DELETE FROM categories_table")
    suspend fun deleteCategories()


    @Query("SELECT * FROM platforms_table")
    suspend fun getPlatforms(): List<PlatformsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatforms(platforms: List<PlatformsEntity>)

    @Query("DELETE FROM platforms_table")
    suspend fun deletePlatforms()


    @Query("SELECT * FROM games_table")
    suspend fun getGames(): List<GamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GamesEntity>)

    @Query("DELETE FROM games_table")
    suspend fun deleteGames()


    @Insert
    suspend fun insertFavorite(favorite: FavoritesEntity)

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT * FROM favorites_table WHERE id = :id")
    suspend fun getFavoriteById(id: Int): FavoritesEntity

    @Query("SELECT * FROM favorites_table")
    suspend fun getAllFavorites(): List<FavoritesEntity>

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()


}