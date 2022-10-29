package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.domain.item.SpecificGameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GameByIdFromService @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(id: Int): SpecificGameItem {

        return gameRepository.getGameByIdFromService(id)

    }

}