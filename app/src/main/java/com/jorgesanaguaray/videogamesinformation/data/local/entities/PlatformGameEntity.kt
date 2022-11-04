package com.jorgesanaguaray.videogamesinformation.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorgesanaguaray.videogamesinformation.domain.item.GameItem

/**
 * Created by Jorge Sanaguaray
 */

@Entity(tableName = "platform_game_table")
data class PlatformGameEntity(

    @PrimaryKey
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String

)

fun GameItem.toPlatformGameEntity() = PlatformGameEntity(id, title, thumbnail, short_description, game_url)