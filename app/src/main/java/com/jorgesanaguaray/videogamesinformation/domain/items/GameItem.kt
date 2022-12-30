package com.jorgesanaguaray.videogamesinformation.domain.items

import com.jorgesanaguaray.videogamesinformation.data.local.entity.*
import com.jorgesanaguaray.videogamesinformation.data.remote.models.GameModel

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
    val release_date: String

)

fun GameModel.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url, genre, platform, publisher, developer, release_date)
fun FavoriteEntity.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url, genre, platform, publisher, developer, release_date)
