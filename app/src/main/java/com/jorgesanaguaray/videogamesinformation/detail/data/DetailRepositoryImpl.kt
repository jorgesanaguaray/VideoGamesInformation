package com.jorgesanaguaray.videogamesinformation.detail.data

import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.SpecificGame
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.detail.domain.DetailRepository

/**
 * Created by Jorge Sanaguaray
 */

class DetailRepositoryImpl(private val gameApi: GameApi) : DetailRepository {

    override suspend fun getGameById(id: Int): Result<SpecificGame> {

        return try {

            Result.success(gameApi.getGameById(id).toDomain())

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}