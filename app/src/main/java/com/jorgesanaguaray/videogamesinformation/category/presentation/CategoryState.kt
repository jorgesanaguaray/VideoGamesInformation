package com.jorgesanaguaray.videogamesinformation.category.presentation

import com.jorgesanaguaray.videogamesinformation.core.items.Game

/**
 * Created by Jorge Sanaguaray
 */

data class CategoryState(

    val games: List<Game> = emptyList(),
    val error: String = "",
    val isTextInputLayout: Boolean = false,
    val isRecyclerView: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false

)
