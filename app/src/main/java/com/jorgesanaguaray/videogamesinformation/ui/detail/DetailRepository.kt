package com.jorgesanaguaray.videogamesinformation.ui.detail

import com.jorgesanaguaray.videogamesinformation.data.remote.GameService
import com.jorgesanaguaray.videogamesinformation.domain.items.SpecificGameItem
import com.jorgesanaguaray.videogamesinformation.domain.items.toSpecificGameItem
import javax.inject.Inject

/**
 * Created by Jorge Sanaguaray
 */

class DetailRepository @Inject constructor(private val gameService: GameService) {

    suspend fun getGameById(id: Int): SpecificGameItem {
        return gameService.getGameById(id).toSpecificGameItem()
    }

}