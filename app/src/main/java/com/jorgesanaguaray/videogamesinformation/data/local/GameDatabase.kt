package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoriesEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GamesEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.PlatformsEntity

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [GameEntity::class, CategoriesEntity::class, PlatformsEntity::class, GamesEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao

}