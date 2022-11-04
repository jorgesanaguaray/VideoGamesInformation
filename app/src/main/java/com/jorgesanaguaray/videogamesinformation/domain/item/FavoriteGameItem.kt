package com.jorgesanaguaray.videogamesinformation.domain.item

import com.jorgesanaguaray.videogamesinformation.data.local.entities.FavoriteGameEntity

/**
 * Created by Jorge Sanaguaray
 */

data class FavoriteGameItem(

    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String

)

fun FavoriteGameEntity.toFavoriteGameItem() = FavoriteGameItem(id, title, thumbnail, short_description, game_url)