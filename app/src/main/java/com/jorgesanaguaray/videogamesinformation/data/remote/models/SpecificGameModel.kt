package com.jorgesanaguaray.videogamesinformation.data.remote.models

data class SpecificGameModel(

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
    val minimum_system_requirements: MinimumSystemRequirements,
    val screenshots: List<Screenshot>

)