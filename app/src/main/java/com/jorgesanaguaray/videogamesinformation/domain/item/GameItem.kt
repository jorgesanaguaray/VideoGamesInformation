package com.jorgesanaguaray.videogamesinformation.domain.item

import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoriesEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GamesEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.PlatformsEntity
import com.jorgesanaguaray.videogamesinformation.data.remote.model.GameModel

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
fun GameEntity.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url)
fun CategoriesEntity.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url)
fun PlatformsEntity.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url)
fun GamesEntity.toGameItem() = GameItem(id, title, thumbnail, short_description, game_url)
