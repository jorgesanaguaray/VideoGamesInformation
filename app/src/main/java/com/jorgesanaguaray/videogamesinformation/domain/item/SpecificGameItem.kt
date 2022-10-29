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
    val status: String,
    val short_description: String,
    val description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val freetogame_profile_url: String,
    val minimum_system_requirements: MinimumSystemRequirements?,
    val screenshots: List<Screenshot>

)

fun SpecificGameModel.toSpecificGameItem() = SpecificGameItem(id = id, title = title, thumbnail = thumbnail, status = status, short_description = short_description, description = description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url, minimum_system_requirements = minimum_system_requirements, screenshots = screenshots)
