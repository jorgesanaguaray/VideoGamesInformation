package com.jorgesanaguaray.videogamesinformation.detail.domain

import com.jorgesanaguaray.videogamesinformation.core.domain.models.SpecificGame

/**
 * Created by Jorge Sanaguaray
 */

interface DetailRepository {

    suspend fun getGameById(id: Int): Result<SpecificGame>

}