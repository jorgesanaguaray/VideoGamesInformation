package com.jorgesanaguaray.videogamesinformation.repo

import com.jorgesanaguaray.videogamesinformation.data.local.GameDao
import com.jorgesanaguaray.videogamesinformation.data.local.entities.*
import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.item.*
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GameRepository @Inject constructor(

    private val gameService: GameService,
    private val gameDao: GameDao

    ) {


    suspend fun insertGame(game: GameEntity) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame() {
        gameDao.deleteGame()
    }

    suspend fun getGameFromDao(): GameItem {
        return gameDao.getGame().toGameItem()
    }

    suspend fun getGamesFromService(): List<GameItem> {

        return gameService.getGames().map {
            it.toGameItem()
        }

    }


    suspend fun insertCategoryGames(games: List<CategoryGameEntity>) {
        gameDao.insertCategoryGames(games)
    }

    suspend fun deleteCategoryGames() {
        gameDao.deleteCategoryGames()
    }

    suspend fun getCategoryGamesFromDao(): List<GameItem> {

        return gameDao.getCategoryGames().map {
            it.toGameItem()
        }

    }

    suspend fun getGamesByCategoryFromService(category: String): List<GameItem> {

        return gameService.getGamesByCategory(category).map {
            it.toGameItem()
        }

    }


    suspend fun insertPlatformGames(platforms: List<PlatformGameEntity>) {
        gameDao.insertPlatformGames(platforms)
    }

    suspend fun deletePlatformGames() {
        gameDao.deletePlatformGames()
    }

    suspend fun getPlatformGamesFromDao(): List<GameItem> {

        return gameDao.getPlatformGames().map {
            it.toGameItem()
        }

    }

    suspend fun getGamesByPlatformFromService(platform: String): List<GameItem> {

        return gameService.getGamesByPlatform(platform).map {
            it.toGameItem()
        }

    }


    suspend fun insertGames(games: List<GamesEntity>) {
        gameDao.insertGames(games)
    }

    suspend fun deleteGames() {
        gameDao.deleteGames()
    }

    suspend fun getGamesFromDao(): List<GameItem> {

        return gameDao.getGames().map {
            it.toGameItem()
        }

    }


    suspend fun getGameByIdFromService(id: Int): SpecificGameItem {
        return gameService.getGameById(id).toSpecificGameItem()
    }


    suspend fun insertFavoriteGame(favoriteGame: FavoriteGameEntity) {
        gameDao.insertFavoriteGame(favoriteGame)
    }

    suspend fun deleteFavoriteGameById(id: Int) {
        gameDao.deleteFavoriteGameById(id)
    }

    suspend fun deleteFavoriteGames() {
        gameDao.deleteFavoriteGames()
    }

    suspend fun getFavoriteGameById(id: Int): FavoriteGameEntity {
        return gameDao.getFavoriteGameById(id)
    }

    suspend fun getFavoriteGames(): List<FavoriteGameItem> {

        return gameDao.getFavoriteGames().map {
            it.toFavoriteGameItem()
        }

    }


}