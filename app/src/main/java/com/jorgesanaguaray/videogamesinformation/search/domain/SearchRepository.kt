package com.jorgesanaguaray.videogamesinformation.search.domain

import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

interface SearchRepository {

    suspend fun getGames(): Result<List<Game>>

    suspend fun getSearchedGames(query: String): Result<List<Game>>

    suspend fun insertFavorite(game: Game)

    suspend fun deleteFavoriteById(id: Int)

    suspend fun isFavorite(id: Int): Boolean

}