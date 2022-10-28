package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.toPlatformsEntity
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class RecommendedPlatformsFromService @Inject constructor(private val gameRepository: GameRepository) {

    private val platforms = listOf("pc", "browser")
    private val platform = platforms.random()

    suspend operator fun invoke(): List<GameItem> {

        val platforms = gameRepository.getPlatformsFromService(platform)
        val platformsNumber = platforms.shuffled().take(5)

        gameRepository.deletePlatforms()
        gameRepository.insertPlatforms(platformsNumber.map {
            it.toPlatformsEntity()
        })

        return platformsNumber

    }

}