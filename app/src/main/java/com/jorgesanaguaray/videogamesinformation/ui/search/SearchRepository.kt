package com.jorgesanaguaray.videogamesinformation.ui.search

import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.toGameItem
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class SearchRepository @Inject constructor(private val gameService: GameService) {

    private lateinit var searchedGames: MutableList<GameItem>

    suspend fun getSearchedGames(query: String): List<GameItem> {

        val games = gameService.getGames().map {
            it.toGameItem()
        }
        searchedGames = ArrayList()

        for (game in games) {
            if (game.title.lowercase().contains(query.lowercase())) searchedGames.add(game)
        }

        return searchedGames.shuffled()

    }

}