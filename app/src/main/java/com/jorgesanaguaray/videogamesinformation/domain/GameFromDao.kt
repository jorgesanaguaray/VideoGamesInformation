package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */
class GameFromDao @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): GameItem {

        return gameRepository.getGameFromDao()

    }

}