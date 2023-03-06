package com.jorgesanaguaray.videogamesinformation.detail.domain

import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.items.SpecificGame

/**
 * Created by Jorge Sanaguaray
 */

interface DetailRepository {

    suspend fun getGameById(id: Int): Result<SpecificGame>

    suspend fun insertFavorite(game: Game)

    suspend fun deleteFavoriteById(id: Int)

    suspend fun isFavorite(id: Int): Boolean

}