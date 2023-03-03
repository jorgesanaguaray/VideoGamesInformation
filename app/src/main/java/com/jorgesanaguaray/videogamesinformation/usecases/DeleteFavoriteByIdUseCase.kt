package com.jorgesanaguaray.videogamesinformation.usecases

import com.jorgesanaguaray.videogamesinformation.usecases.domain.UseCaseRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class DeleteFavoriteByIdUseCase @Inject constructor(private val useCaseRepository: UseCaseRepository) {

    suspend operator fun invoke(id: Int) {

        useCaseRepository.deleteFavoriteById(id)

    }

}