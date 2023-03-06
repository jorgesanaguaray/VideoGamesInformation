package com.jorgesanaguaray.videogamesinformation.home.data

import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDatabase
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.home.domain.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class HomeRepositoryImpl(private val gameApi: GameApi, private val favoriteDao: FavoriteDao) : HomeRepository {

    override suspend fun getGamesA(): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGames().shuffled().take(10).map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun getGamesB(): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGames().shuffled().take(10).map { it.toDomain() })

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