package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GetGamesByCategoryFromServiceUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(category: String): List<GameItem> {

        val categories = gameRepository.getGamesByCategoryFromService(category)
        return categories.shuffled()

    }

}