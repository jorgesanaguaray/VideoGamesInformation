package com.jorgesanaguaray.videogamesinformation.data.remote.model

data class SpecificGameModel(

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
    val minimum_system_requirements: MinimumSystemRequirements,
    val screenshots: List<Screenshot>

)