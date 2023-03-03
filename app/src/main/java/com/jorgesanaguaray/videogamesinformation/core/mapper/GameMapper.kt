package com.jorgesanaguaray.videogamesinformation.core.mapper

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteEntity
import com.jorgesanaguaray.videogamesinformation.core.remote.dtos.GameDto
import com.jorgesanaguaray.videogamesinformation.core.remote.dtos.SpecificGameDto
import com.jorgesanaguaray.videogamesinformation.core.items.Game
import com.jorgesanaguaray.videogamesinformation.core.items.SpecificGame

/**
 * Created by Jorge Sanaguaray
 */

fun GameDto.toDomain(): Game {

    return Game(id, title, thumbnail, short_description, game_url, genre, platform, publisher, developer, release_date)

}

fun FavoriteEntity.toDomain(): Game {

    return Game(id, title, thumbnail, short_description, game_url, genre, platform, publisher, developer, release_date)

}

fun SpecificGameDto.toDomain(): SpecificGame {

    return SpecificGame(id, title, thumbnail, status, short_description, description, game_url, genre, platform, publisher, developer, release_date, minimum_system_requirements, screenshots)

}

fun Game.toDatabase(): FavoriteEntity {

    return FavoriteEntity(id, title, thumbnail, short_description, game_url, genre, platform, publisher, developer, release_date)

}