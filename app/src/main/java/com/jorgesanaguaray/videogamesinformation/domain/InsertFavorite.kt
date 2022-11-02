package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.FavoritesEntity
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class InsertFavorite @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(favoritesEntity: FavoritesEntity) {

        gameRepository.insertFavorite(favoritesEntity)

    }

}