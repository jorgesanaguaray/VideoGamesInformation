package com.jorgesanaguaray.videogamesinformation.core.domain

import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game

/**
 * Created by Jorge Sanaguaray
 */

interface CoreRepository {

    suspend fun insertFavorite(game: Game)

    suspend fun deleteFavoriteById(id: Int)

    suspend fun isFavorite(id: Int): Boolean

}