package com.jorgesanaguaray.videogamesinformation.favorite.data

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDatabase
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.favorite.domain.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class FavoriteRepositoryImpl(private val favoriteDao: FavoriteDao) : FavoriteRepository {

    override suspend fun getFavorites(): Result<List<Game>> {

        return try {

            Result.success(favoriteDao.getFavorites().shuffled().map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun deleteFavorites() {
        favoriteDao.deleteFavorites()
    }

    override suspend fun insertFavorite(game: Game) {
        favoriteDao.insertFavorite(game.toDatabase())
    }

    override suspend fun deleteFavoriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }

    override suspend fun isFavorite(id: Int): Boolean {

        var favoriteEntity: FavoriteEntity?

        runBlocking(Dispatchers.IO) {
            favoriteEntity = favoriteDao.getFavoriteById(id)
        }

        if (favoriteEntity == null) return false
        return true

    }

}