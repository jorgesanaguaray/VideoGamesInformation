package com.jorgesanaguaray.videogamesinformation.favorite.di

import com.jorgesanaguaray.videogamesinformation.core.data.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.favorite.data.FavoriteRepositoryImpl
import com.jorgesanaguaray.videogamesinformation.favorite.domain.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jorge Sanaguaray
 */

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Singleton
    @Provides
    fun provideRepository(favoriteDao: FavoriteDao): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDao)
    }

}