package com.jorgesanaguaray.videogamesinformation.data.remote

import com.jorgesanaguaray.videogamesinformation.data.remote.models.GameModel
import com.jorgesanaguaray.videogamesinformation.data.remote.models.SpecificGameModel
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.CATEGORY_ENDPOINT
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.GAMES_ENDPOINT
import com.jorgesanaguaray.videogamesinformation.util.Constants.Companion.GAME_ID_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jorge Sanaguaray
 */

interface GameApi {

    @GET(GAMES_ENDPOINT)
    suspend fun getGames(): Response<List<GameModel>>

    @GET(CATEGORY_ENDPOINT)
    suspend fun getGamesByCategory(@Query(value = "category") category: String): Response<List<GameModel>>

    @GET(GAME_ID_ENDPOINT)
    suspend fun getGameById(@Query(value = "id") id: Int): Response<SpecificGameModel>

}