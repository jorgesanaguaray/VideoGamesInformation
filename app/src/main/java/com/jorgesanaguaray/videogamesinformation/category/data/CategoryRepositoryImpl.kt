package com.jorgesanaguaray.videogamesinformation.category.data

import com.jorgesanaguaray.videogamesinformation.category.domain.CategoryRepository
import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDatabase
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class CategoryRepositoryImpl(private val gameApi: GameApi, private val favoriteDao: FavoriteDao) : CategoryRepository {

    override suspend fun getGamesByCategory(category: String): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGamesByCategory(category).shuffled().map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

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