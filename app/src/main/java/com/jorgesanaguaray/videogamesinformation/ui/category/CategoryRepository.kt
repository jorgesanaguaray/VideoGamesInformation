package com.jorgesanaguaray.videogamesinformation.ui.category

import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.toGameItem
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class CategoryRepository @Inject constructor(private val gameService: GameService) {

    suspend fun getGamesByCategory(category: String): List<GameItem> {

        val games = gameService.getGamesByCategory(category).map {
            it.toGameItem()
        }

        return games.shuffled()

    }

}