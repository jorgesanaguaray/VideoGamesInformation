package com.jorgesanaguaray.videogamesinformation.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

}