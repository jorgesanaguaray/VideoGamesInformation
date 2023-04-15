package com.jorgesanaguaray.videogamesinformation.category.data

import com.jorgesanaguaray.videogamesinformation.category.domain.CategoryRepository
import com.jorgesanaguaray.videogamesinformation.core.data.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain

/**
 * Created by Jorge Sanaguaray
 */

class CategoryRepositoryImpl(private val gameApi: GameApi) : CategoryRepository {

    override suspend fun getGamesByCategory(category: String): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGamesByCategory(category).shuffled().map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}