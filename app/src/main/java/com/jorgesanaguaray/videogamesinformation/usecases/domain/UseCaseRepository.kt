package com.jorgesanaguaray.videogamesinformation.usecases.domain

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

interface UseCaseRepository {

    suspend fun insertFavorite(game: Game)

    suspend fun deleteFavoriteById(id: Int)

    suspend fun getFavoriteById(id: Int): FavoriteEntity

}