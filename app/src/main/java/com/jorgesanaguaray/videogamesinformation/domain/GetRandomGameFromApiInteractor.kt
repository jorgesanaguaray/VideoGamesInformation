package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.toDatabase
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GetRandomGameFromApiInteractor @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): GameItem {

        val games = gameRepository.getGamesFromApi()
        gameRepository.deleteGames()
        gameRepository.insertGames(games.map { it.toDatabase() })
        return games.random()

    }

}