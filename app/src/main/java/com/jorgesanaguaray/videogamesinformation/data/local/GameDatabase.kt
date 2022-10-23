package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoryEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.PlatformEntity

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [GameEntity::class, CategoryEntity::class, PlatformEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao

}