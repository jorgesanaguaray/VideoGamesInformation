package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.entities.toCategoryGameEntity
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem
import com.jorgesanaguaray.videogamesinformation.repo.GameRepository
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class Get5GamesByCategoryFromServiceUseCase @Inject constructor(private val gameRepository: GameRepository) {

    private val categories = listOf("mmorpg", "shooter", "strategy", "moba", "racing", "sports", "social", "sandbox", "open-world", "survival", "pvp", "pve", "pixel", "voxel", "zombie", "turn-based", "first-person", "third-Person", "top-down", "tank", "space", "sailing", "side-scroller", "superhero", "permadeath", "card", "battle-royale", "mmo", "mmofps", "mmotps", "3d", "2d", "anime", "fantasy", "sci-fi", "fighting", "action-rpg", "action", "military", "martial-arts", "flight", "low-spec", "tower-defense", "horror", "mmorts")
    private val category = categories.random()

    suspend operator fun invoke(): List<GameItem> {

        val categories = gameRepository.getGamesByCategoryFromService(category)
        val categoriesNumber = categories.shuffled().take(5)

        gameRepository.deleteCategoryGames()
        gameRepository.insertCategoryGames(categoriesNumber.map {
            it.toCategoryGameEntity()
        })

        return categoriesNumber

    }

}