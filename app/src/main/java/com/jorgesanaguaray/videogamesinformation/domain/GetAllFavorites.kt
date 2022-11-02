package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.domain.item.FavoritesItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class GetAllFavorites @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): List<FavoritesItem> {

        val games = gameRepository.getAllFavorites()
        return games.shuffled()

    }

}