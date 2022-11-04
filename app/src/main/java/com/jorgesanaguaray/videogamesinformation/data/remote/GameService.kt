package com.jorgesanaguaray.videogamesinformation.data.remote

import com.jorgesanaguaray.videogamesinformation.data.remote.model.GameModel
import com.jorgesanaguaray.videogamesinformation.data.remote.model.SpecificGameModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GameService @Inject constructor(private val gameApi: GameApi) {

    suspend fun getGames(): List<GameModel> {

        return withContext(Dispatchers.IO) {
            val games = gameApi.getGames()
            games.body() ?: emptyList()
        }

    }

    suspend fun getGamesByCategory(category: String): List<GameModel> {

        return withContext(Dispatchers.IO) {
            val games = gameApi.getGamesByCategory(category)
            games.body() ?: emptyList()
        }

    }

    suspend fun getGamesByPlatform(platform: String): List<GameModel> {

        return withContext(Dispatchers.IO) {
            val games = gameApi.getGamesByPlatform(platform)
            games.body() ?: emptyList()
        }

    }

    suspend fun getGameById(id: Int): SpecificGameModel {

        return withContext(Dispatchers.IO) {
            val game = gameApi.getGameById(id)
            game.body()!!
        }

    }

}