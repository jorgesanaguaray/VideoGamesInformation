package com.jorgesanaguaray.videogamesinformation.usecases

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.usecases.domain.UseCaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class IsFavoriteUseCase @Inject constructor(private val useCaseRepository: UseCaseRepository) {

    suspend operator fun invoke(id: Int): Boolean {

        var favoriteEntity: FavoriteEntity?

        runBlocking(Dispatchers.IO) {
            favoriteEntity = useCaseRepository.getFavoriteById(id)
        }

        if (favoriteEntity == null) return false
        return true

    }

}