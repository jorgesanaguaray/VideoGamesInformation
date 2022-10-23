package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.toPlatformEntity
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class PlatformsFromService @Inject constructor(private val gameRepository: GameRepository) {

    private val platforms = listOf("pc", "browser")
    private val platform = platforms.random()

    suspend operator fun invoke(): List<GameItem> {

        val platforms = gameRepository.getPlatformsFromService(platform)

        gameRepository.deletePlatforms()
        gameRepository.insertPlatforms(platforms.map {
            it.toPlatformEntity()
        })

        return platforms.shuffled()

    }

}