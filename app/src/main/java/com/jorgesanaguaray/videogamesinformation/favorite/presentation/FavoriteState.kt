package com.jorgesanaguaray.videogamesinformation.favorite.presentation

import com.jorgesanaguaray.videogamesinformation.core.items.Game

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
