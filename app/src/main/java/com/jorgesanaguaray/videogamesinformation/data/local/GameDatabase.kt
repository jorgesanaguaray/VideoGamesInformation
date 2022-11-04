package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgesanaguaray.videogamesinformation.data.local.entities.*

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [GameEntity::class, CategoryGameEntity::class, PlatformGameEntity::class, GamesEntity::class, FavoriteGameEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao

}