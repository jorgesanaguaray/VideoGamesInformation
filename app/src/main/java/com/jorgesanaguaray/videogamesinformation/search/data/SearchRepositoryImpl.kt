package com.jorgesanaguaray.videogamesinformation.search.data

import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDatabase
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.search.domain.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by Jorge Sanaguaray
 */

class SearchRepositoryImpl(private val gameApi: GameApi, private val favoriteDao: FavoriteDao) : SearchRepository {

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

    override suspend fun insertFavorite(game: Game) {
        favoriteDao.insertFavorite(game.toDatabase())
    }

    override suspend fun deleteFavoriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }

    override suspend fun isFavorite(id: Int): Boolean {

        var favoriteEntity: FavoriteEntity?

        runBlocking(Dispatchers.IO) {
            favoriteEntity = favoriteDao.getFavoriteById(id)
        }

        if (favoriteEntity == null) return false
        return true

    }

}