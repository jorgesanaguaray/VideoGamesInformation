package com.jorgesanaguaray.videogamesinformation.core.items

import com.jorgesanaguaray.videogamesinformation.core.remote.dtos.MinimumSystemRequirements
import com.jorgesanaguaray.videogamesinformation.core.remote.dtos.Screenshot

/**
 * Created by Jorge Sanaguaray
 */

data class SpecificGame(

    val id: Int,
    val title: String,
    val thumbnail: String,
    val status: String,
    val short_description: String,
    val description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val minimum_system_requirements: MinimumSystemRequirements?,
    val screenshots: List<Screenshot>

)