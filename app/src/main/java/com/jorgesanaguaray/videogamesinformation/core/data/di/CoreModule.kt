package com.jorgesanaguaray.videogamesinformation.core.data.di

import android.content.Context
import androidx.room.Room
import com.jorgesanaguaray.videogamesinformation.Constants
import com.jorgesanaguaray.videogamesinformation.core.data.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.Constants.Companion.BASE_URL
import com.jorgesanaguaray.videogamesinformation.core.data.CoreRepositoryImpl
import com.jorgesanaguaray.videogamesinformation.core.data.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.data.local.FavoriteDatabase
import com.jorgesanaguaray.videogamesinformation.core.domain.CoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Jorge Sanaguaray
 */

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideGameApi(retrofit: Retrofit): GameApi {
        return retrofit.create(GameApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, FavoriteDatabase::class.java, Constants.DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase) = favoriteDatabase.getFavoriteDao()

    @Singleton
    @Provides
    fun provideCoreRepository(favoriteDao: FavoriteDao): CoreRepository {
        return CoreRepositoryImpl(favoriteDao)
    }

}