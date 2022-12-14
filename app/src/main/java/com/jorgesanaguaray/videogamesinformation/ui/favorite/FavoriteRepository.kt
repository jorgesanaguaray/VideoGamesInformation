package com.jorgesanaguaray.videogamesinformation.ui.favorite

import com.jorgesanaguaray.videogamesinformation.data.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.toGameItem
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    suspend fun getFavorites(): List<GameItem> {

        val games = favoriteDao.getFavorites().map {
            it.toGameItem()
        }

        return games.shuffled()

    }

    suspend fun deleteFavorites() {
        favoriteDao.deleteFavorites()
    }

}