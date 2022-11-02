package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class DeleteFavoriteById @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(id: Int) {

        gameRepository.deleteFavoriteById(id)

    }

}