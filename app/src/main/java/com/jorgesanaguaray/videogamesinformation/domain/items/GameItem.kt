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
    val game_url: String

)

fun GameModel.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url)
fun FavoriteEntity.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url)
