package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class DeleteFavoriteGamesUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke() {

        gameRepository.deleteFavoriteGames()

    }

}