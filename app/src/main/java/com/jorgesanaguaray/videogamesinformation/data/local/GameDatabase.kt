package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgesanaguaray.videogamesinformation.data.local.entities.CategoryEntity
import com.jorgesanaguaray.videogamesinformation.data.local.entities.GameEntity

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [GameEntity::class, CategoryEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao

}