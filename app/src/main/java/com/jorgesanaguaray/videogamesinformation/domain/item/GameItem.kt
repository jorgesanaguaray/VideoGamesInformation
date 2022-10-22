package com.jorgesanaguaray.videogamesinformation.domain.item

import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoryEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.remote.model.GameModel

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

fun GameModel.toGameItem() = GameItem(id = id, title = title, thumbnail = thumbnail, short_description = short_description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url)
fun GameEntity.toGameItem() = GameItem(id = id, title = title, thumbnail = thumbnail, short_description = short_description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url)
fun CategoryEntity.toGameItem() = GameItem(id = id, title = title, thumbnail = thumbnail, short_description = short_description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url)
