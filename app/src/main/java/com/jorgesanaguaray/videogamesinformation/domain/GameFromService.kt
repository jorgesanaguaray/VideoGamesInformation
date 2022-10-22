package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.toGameEntity
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GameFromService @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): GameItem {

        val games = gameRepository.getGamesFromService()
        val randomGame = games.random()

        gameRepository.deleteGame()
        gameRepository.insertGame(randomGame.toGameEntity())

        return randomGame

    }

}