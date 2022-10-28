package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class CategoriesGames @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(category: String): List<GameItem> {

        val categories = gameRepository.getCategoriesFromService(category)
        return categories.shuffled()

    }

}