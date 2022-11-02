package com.jorgesanaguaray.videogamesinformation.domain.item

import com.jorgesanaguaray.videogamesinformation.data.remote.model.MinimumSystemRequirements
import com.jorgesanaguaray.videogamesinformation.data.remote.model.Screenshot
import com.jorgesanaguaray.videogamesinformation.data.remote.model.SpecificGameModel

/**
 * Created by Jorge Sanaguaray
 */

data class SpecificGameItem(

    val id: Int,
    val title: String,
    val thumbnail: String,
    val game_url: String,
    val short_description: String,
    val description: String,
    val status: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val minimum_system_requirements: MinimumSystemRequirements?,
    val screenshots: List<Screenshot>

)

fun SpecificGameModel.toSpecificGameItem() = SpecificGameItem(id, title, thumbnail, game_url, short_description, description, status, genre, platform, publisher, developer, release_date, minimum_system_requirements, screenshots)
