package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.toPlatformGameEntity
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class Get5GamesByPlatformFromServiceUseCase @Inject constructor(private val gameRepository: GameRepository) {

    private val platforms = listOf("pc", "browser")
    private val platform = platforms.random()

    suspend operator fun invoke(): List<GameItem> {

        val platforms = gameRepository.getGamesByPlatformFromService(platform)
        val platformsNumber = platforms.shuffled().take(5)

        gameRepository.deletePlatformGames()
        gameRepository.insertPlatformGames(platformsNumber.map {
            it.toPlatformGameEntity()
        })

        return platformsNumber

    }

}