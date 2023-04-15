package com.jorgesanaguaray.videogamesinformation.search.domain

import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game

/**
 * Created by Jorge Sanaguaray
 */

interface SearchRepository {

    suspend fun getGames(): Result<List<Game>>

    suspend fun getSearchedGames(query: String): Result<List<Game>>

}