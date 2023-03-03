package com.jorgesanaguaray.videogamesinformation.home.data

import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.mapper.toDomain
import com.jorgesanaguaray.videogamesinformation.home.domain.HomeRepository

/**
 * Created by Jorge Sanaguaray
 */

class HomeRepositoryImpl(private val gameApi: GameApi) : HomeRepository {

    override suspend fun getGamesA(): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGames().shuffled().take(10).map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun getGamesB(): Result<List<Game>> {

        return try {

            Result.success(gameApi.getGames().shuffled().take(10).map { it.toDomain() })

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}