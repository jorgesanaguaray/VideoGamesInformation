package com.jorgesanaguaray.videogamesinformation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorgesanaguaray.videogamesinformation.domain.items.GameItem

/**
 * Created by Jorge Sanaguaray
 */

@Entity(tableName = "favorite_table")
data class FavoriteEntity(

    @PrimaryKey
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String

)

fun GameItem.toFavoriteEntity() = FavoriteEntity(id, title, thumbnail, short_description, game_url)
