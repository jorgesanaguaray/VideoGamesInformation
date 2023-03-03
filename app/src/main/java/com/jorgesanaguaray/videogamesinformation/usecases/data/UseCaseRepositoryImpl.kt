package com.jorgesanaguaray.videogamesinformation.usecases.data

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.usecases.domain.UseCaseRepository
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDatabase

/**
 * Created by Jorge Sanaguaray
 */

class UseCaseRepositoryImpl(private val favoriteDao: FavoriteDao) : UseCaseRepository {

    override suspend fun insertFavorite(game: Game) {
        favoriteDao.insertFavorite(game.toDatabase())
    }

    override suspend fun deleteFavoriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }

    override suspend fun getFavoriteById(id: Int): FavoriteEntity {
        return favoriteDao.getFavoriteById(id)
    }

}