package com.jorgesanaguaray.videogamesinformation.favorite.domain

import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

interface FavoriteRepository {

    suspend fun getFavorites(): Result<List<Game>>

    suspend fun deleteFavorites()

}