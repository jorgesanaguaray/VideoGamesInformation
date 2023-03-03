package com.jorgesanaguaray.videogamesinformation.search.data

import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.search.domain.SearchRepository

/**
 * Created by Jorge Sanaguaray
 */

class SearchRepositoryImpl(private val gameApi: GameApi) : SearchRepository {

    private lateinit var searchedGames: MutableList<Game>

    override suspend fun getGames(): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGames().shuffled().map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun getSearchedGames(query: String): Result<List<Game>> {

        return try {

            val games = gameApi.getGames().map { it.toDomain() }
            searchedGames = ArrayList()

            for (game in games) {
                if (game.title.lowercase().contains(query.lowercase())) searchedGames.add(game)
            }

            Result.success(searchedGames.shuffled())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}