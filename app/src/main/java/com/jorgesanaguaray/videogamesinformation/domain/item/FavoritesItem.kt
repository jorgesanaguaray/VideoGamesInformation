package com.jorgesanaguaray.videogamesinformation.domain.item

import com.jorgesanaguaray.videogamesinformation.data.local.entities.FavoritesEntity

/**
 * Created by Jorge Sanaguaray
 */

data class FavoritesItem(

    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String

)

fun FavoritesEntity.toFavoritesItem() = FavoritesItem(id, title, thumbnail, short_description, game_url)