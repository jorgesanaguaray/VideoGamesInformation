package com.jorgesanaguaray.videogamesinformation.favorite.data

import com.jorgesanaguaray.videogamesinformation.core.data.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
import com.jorgesanaguaray.videogamesinformation.core.data.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.favorite.domain.FavoriteRepository

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

}