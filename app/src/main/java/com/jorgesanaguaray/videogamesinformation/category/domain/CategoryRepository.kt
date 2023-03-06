package com.jorgesanaguaray.videogamesinformation.category.domain

import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

interface CategoryRepository {

    suspend fun getGamesByCategory(category: String): Result<List<Game>>

    suspend fun insertFavorite(game: Game)

    suspend fun deleteFavoriteById(id: Int)

    suspend fun isFavorite(id: Int): Boolean

}