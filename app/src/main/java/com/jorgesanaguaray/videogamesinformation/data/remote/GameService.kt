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

    suspend fun getCategories(category: String): List<GameModel> {

        return withContext(Dispatchers.IO) {
            val categories = gameApi.getCategories(category)
            categories.body() ?: emptyList()
        }

    }

    suspend fun getPlatforms(platform: String): List<GameModel> {

        return withContext(Dispatchers.IO) {
            val platforms = gameApi.getPlatforms(platform)
            platforms.body() ?: emptyList()
        }

    }

    suspend fun getGameById(id: Int): SpecificGameModel {

        return withContext(Dispatchers.IO) {
            val game = gameApi.getGameById(id)
            game.body()!!
        }

    }

}