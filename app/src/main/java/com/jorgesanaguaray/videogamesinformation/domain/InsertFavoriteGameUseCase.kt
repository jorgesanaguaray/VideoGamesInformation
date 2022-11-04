package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.FavoriteGameEntity
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class InsertFavoriteGameUseCase @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(favorite: FavoriteGameEntity) {

        gameRepository.insertFavoriteGame(favorite)

    }

}