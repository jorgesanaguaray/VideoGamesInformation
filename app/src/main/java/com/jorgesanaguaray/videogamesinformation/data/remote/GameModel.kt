package com.jorgesanaguaray.videogamesinformation.data.remote

/**
 * Created by Jorge Sanaguaray
 */

data class GameModel(

    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val freetogame_profile_url: String

)
