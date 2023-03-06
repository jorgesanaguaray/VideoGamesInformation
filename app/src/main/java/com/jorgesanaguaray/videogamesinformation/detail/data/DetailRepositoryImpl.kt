package com.jorgesanaguaray.videogamesinformation.detail.data

import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.SpecificGame
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDatabase
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.detail.domain.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class DetailRepositoryImpl(private val gameApi: GameApi, private val favoriteDao: FavoriteDao) : DetailRepository {

    override suspend fun getGameById(id: Int): Result<SpecificGame> {

        return try {

            Result.success(gameApi.getGameById(id).toDomain())

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