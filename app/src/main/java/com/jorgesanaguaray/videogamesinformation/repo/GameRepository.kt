package com.jorgesanaguaray.videogamesinformation.repo

import com.jorgesanaguaray.videogamesinformation.data.local.GameDao
import com.jorgesanaguaray.videogamesinformation.data.local.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.toDomain
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GameRepository @Inject constructor(
    private val gameService: GameService,
    private val gameDao: GameDao
    ) {

    suspend fun getGamesFromApi(): List<GameItem> {

        val games = gameService.getGames()
        return games.map { it.toDomain() }

    }

    suspend fun getGamesFromDatabase(): List<GameItem> {

        val games = gameDao.getGames()
        return games.map { it.toDomain() }

    }

    suspend fun insertGames(games: List<GameEntity>) {
        gameDao.insertGames(games)
    }

    suspend fun deleteGames() {
        gameDao.deleteGames()
    }

}