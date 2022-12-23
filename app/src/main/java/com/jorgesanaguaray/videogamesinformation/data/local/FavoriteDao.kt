package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgesanaguaray.videogamesinformation.data.local.entity.*

/**
 * Created by Jorge Sanaguaray
 */

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    suspend fun getFavoriteById(id: Int): FavoriteEntity

    @Query("SELECT * FROM favorite_table")
    suspend fun getFavorites(): List<FavoriteEntity>

    @Query("DELETE FROM favorite_table")
    suspend fun deleteFavorites()

}