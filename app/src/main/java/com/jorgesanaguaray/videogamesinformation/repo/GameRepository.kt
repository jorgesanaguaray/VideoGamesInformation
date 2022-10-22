package com.jorgesanaguaray.videogamesinformation.repo

import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoryEntity
import com.jorgesanaguaray.videogamesinformation.data.local.GameDao
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.item.toGameItem
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GameRepository @Inject constructor(

    private val gameService: GameService,
    private val gameDao: GameDao

    ) {


    suspend fun getGamesFromService(): List<GameItem> {

        val games = gameService.getGames()
        return games.map {
            it.toGameItem()
        }

    }

    suspend fun getGameFromDao(): GameItem {

        val game = gameDao.getGame()
        return game.toGameItem()

    }

    suspend fun insertGame(game: GameEntity) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame() {
        gameDao.deleteGame()
    }


    suspend fun getCategoriesFromService(category: String): List<GameItem> {

        val categories = gameService.getCategories(category)
        return categories.map {
            it.toGameItem()
        }

    }

    suspend fun getCategoriesFromDao(): List<GameItem> {

        val categories = gameDao.getCategories()
        return categories.map {
            it.toGameItem()
        }

    }

    suspend fun insertCategories(categories: List<CategoryEntity>) {
        gameDao.insertCategories(categories)
    }

    suspend fun deleteCategories() {
        gameDao.deleteCategories()
    }


}