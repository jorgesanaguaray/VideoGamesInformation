package com.jorgesanaguaray.videogamesinformation.core.data

import com.jorgesanaguaray.videogamesinformation.core.data.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.data.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.domain.CoreRepository
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
import com.jorgesanaguaray.videogamesinformation.core.data.mapper.toDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class CoreRepositoryImpl(private val favoriteDao: FavoriteDao) : CoreRepository {

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