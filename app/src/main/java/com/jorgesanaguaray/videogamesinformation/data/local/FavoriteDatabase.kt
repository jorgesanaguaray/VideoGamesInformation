package com.jorgesanaguaray.videogamesinformation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgesanaguaray.videogamesinformation.data.local.entity.*

/**
 * Created by Jorge Sanaguaray
 */

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

}