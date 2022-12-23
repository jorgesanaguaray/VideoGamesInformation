package com.jorgesanaguaray.videogamesinformation.domain.usecases

import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem
import com.jorgesanaguaray.videogamesinformation.domain.repo.DomainRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class InsertFavoriteUseCase @Inject constructor(private val domainRepository: DomainRepository) {

    suspend operator fun invoke(gameItem: GameItem) {

        domainRepository.insertFavorite(gameItem)

    }

}