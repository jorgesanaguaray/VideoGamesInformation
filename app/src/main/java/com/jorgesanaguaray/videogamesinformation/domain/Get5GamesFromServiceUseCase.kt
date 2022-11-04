package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.toGamesEntity
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class Get5GamesFromServiceUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): List<GameItem> {

        val games = gameRepository.getGamesFromService()
        val gamesNumber = games.shuffled().take(5)

        gameRepository.deleteGames()
        gameRepository.insertGames(gamesNumber.map {
            it.toGamesEntity()
        })

        return gamesNumber

    }

}