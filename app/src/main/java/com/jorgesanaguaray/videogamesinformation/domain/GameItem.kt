package com.jorgesanaguaray.videogamesinformation.domain

import com.jorgesanaguaray.videogamesinformation.data.local.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.remote.GameModel

/**
 * Created by Jorge Sanaguaray
 */

data class GameItem(

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

fun GameModel.toDomain() = GameItem(id = id, title = title, thumbnail = thumbnail, short_description = short_description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url)
fun GameEntity.toDomain() = GameItem(id = id, title = title, thumbnail = thumbnail, short_description = short_description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url)
