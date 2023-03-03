package com.jorgesanaguaray.videogamesinformation.search.presentation

import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

data class SearchState(

    val games: List<Game> = emptyList(),
    val error: String = "",
    val isSearchView: Boolean = false,
    val isRecyclerView: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false

)
