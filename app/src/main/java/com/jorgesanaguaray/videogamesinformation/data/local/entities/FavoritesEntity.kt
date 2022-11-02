package com.jorgesanaguaray.videogamesinformation.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Jorge Sanaguaray
 */

@Entity(tableName = "favorites_table")
data class FavoritesEntity(

    @PrimaryKey
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String

)
