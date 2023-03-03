package com.jorgesanaguaray.videogamesinformation.usecases

import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.usecases.domain.UseCaseRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class InsertFavoriteUseCase @Inject constructor(private val useCaseRepository: UseCaseRepository) {

    suspend operator fun invoke(gameItem: Game) {

        useCaseRepository.insertFavorite(gameItem)

    }

}