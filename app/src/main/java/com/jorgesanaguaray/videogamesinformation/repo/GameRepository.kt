package com.jorgesanaguaray.videogamesinformation.repo

import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoriesEntity
import com.jorgesanaguaray.videogamesinformation.data.local.GameDao
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GamesEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.PlatformsEntity
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

    suspend fun insertCategories(categories: List<CategoriesEntity>) {
        gameDao.insertCategories(categories)
    }

    suspend fun deleteCategories() {
        gameDao.deleteCategories()
    }


    suspend fun getPlatformsFromService(platform: String): List<GameItem> {

        val platforms = gameService.getPlatforms(platform)
        return platforms.map {
            it.toGameItem()
        }

    }

    suspend fun getPlatformsFromDao(): List<GameItem> {

        val platforms = gameDao.getPlatforms()
        return platforms.map {
            it.toGameItem()
        }

    }

    suspend fun insertPlatforms(platforms: List<PlatformsEntity>) {
        gameDao.insertPlatforms(platforms)
    }

    suspend fun deletePlatforms() {
        gameDao.deletePlatforms()
    }


    suspend fun getGamesFromDao(): List<GameItem> {

        val games = gameDao.getGames()
        return games.map {
            it.toGameItem()
        }

    }

    suspend fun insertGames(games: List<GamesEntity>) {
        gameDao.insertGames(games)
    }

    suspend fun deleteGames() {
        gameDao.deleteGames()
    }


}