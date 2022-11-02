package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.FavoritesEntity
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GetFavoriteById @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(id: Int): FavoritesEntity {

        return gameRepository.getFavoriteById(id)

    }

}