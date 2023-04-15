package com.jorgesanaguaray.videogamesinformation.home.presentation

import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game

/**
 * Created by Jorge Sanaguaray
 */

data class HomeState(

    val gamesA: List<Game> = emptyList(),
    val gamesB: List<Game> = emptyList(),
    val error: String = "",
    val isContent: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false

)
