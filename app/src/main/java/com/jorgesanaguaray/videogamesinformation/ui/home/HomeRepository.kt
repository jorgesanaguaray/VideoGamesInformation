package com.jorgesanaguaray.videogamesinformation.ui.home

import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.toGameItem
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class HomeRepository @Inject constructor(private val gameService: GameService) {

    suspend fun getGames(): List<GameItem> {

        val games = gameService.getGames().shuffled().take(10)

        return games.map {
            it.toGameItem()
        }

    }

}