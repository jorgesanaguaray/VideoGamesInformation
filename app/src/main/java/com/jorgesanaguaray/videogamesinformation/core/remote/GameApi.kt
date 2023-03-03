package com.jorgesanaguaray.videogamesinformation.core.remote

import com.jorgesanaguaray.videogamesinformation.core.remote.dtos.GameDto
import com.jorgesanaguaray.videogamesinformation.core.remote.dtos.SpecificGameDto
import com.jorgesanaguaray.videogamesinformation.Constants.Companion.CATEGORY_ENDPOINT
import com.jorgesanaguaray.videogamesinformation.Constants.Companion.GAMES_ENDPOINT
import com.jorgesanaguaray.videogamesinformation.Constants.Companion.GAME_ID_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jorge Sanaguaray
 */

interface GameApi {

    @GET(GAMES_ENDPOINT)
    suspend fun getGames(): List<GameDto>

    @GET(CATEGORY_ENDPOINT)
    suspend fun getGamesByCategory(@Query(value = "category") category: String): List<GameDto>

    @GET(GAME_ID_ENDPOINT)
    suspend fun getGameById(@Query(value = "id") id: Int): SpecificGameDto

}