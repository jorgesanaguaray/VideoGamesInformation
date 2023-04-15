package com.jorgesanaguaray.videogamesinformation.favorite.presentation

import com.jorgesanaguaray.videogamesinformation.core.domain.models.Game

/**
 * Created by Jorge Sanaguaray
 */

data class FavoriteState(

    val games: List<Game> = emptyList(),
    val isRecyclerView: Boolean = false,
    val isFloatingButton: Boolean = false,
    val isNoFavorites: Boolean = false,
    val isLoading: Boolean = false

)
