package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorgesanaguaray.videogamesinformation.domain.GameItem

/**
 * Created by Jorge Sanaguaray
 */

@Entity(tableName = "game_table")
data class GameEntity(

    @PrimaryKey
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

fun GameItem.toDatabase() = GameEntity(id = id, title = title, thumbnail = thumbnail, short_description = short_description, game_url = game_url, genre = genre, platform = platform, publisher = publisher, developer = developer, release_date = release_date, freetogame_profile_url = freetogame_profile_url)
