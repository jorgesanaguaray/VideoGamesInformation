package com.jorgesanaguaray.videogamesinformation.home.domain

import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

interface HomeRepository {

    suspend fun getGamesA(): Result<List<Game>>

    suspend fun getGamesB(): Result<List<Game>>

}