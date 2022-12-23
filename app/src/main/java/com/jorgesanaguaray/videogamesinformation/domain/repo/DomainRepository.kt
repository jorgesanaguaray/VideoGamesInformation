package com.jorgesanaguaray.videogamesinformation.domain.repo

import com.jorgesanaguaray.videogamesinformation.data.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.data.local.entity.*
import com.jorgesanaguaray.videogamesinformation.domain.items.*
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class DomainRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    suspend fun insertFavorite(gameItem: GameItem) {
        favoriteDao.insertFavorite(gameItem.toFavoriteEntity())
    }

    suspend fun deleteFavoriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }

    suspend fun getFavoriteById(id: Int): FavoriteEntity {
        return favoriteDao.getFavoriteById(id)
    }

}