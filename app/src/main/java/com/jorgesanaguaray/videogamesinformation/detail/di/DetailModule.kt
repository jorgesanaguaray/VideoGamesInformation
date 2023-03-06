package com.jorgesanaguaray.videogamesinformation.detail.di

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.detail.data.DetailRepositoryImpl
import com.jorgesanaguaray.videogamesinformation.detail.domain.DetailRepository
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
object DetailModule {

    @Singleton
    @Provides
    fun provideRepository(gameApi: GameApi, favoriteDao: FavoriteDao): DetailRepository {
        return DetailRepositoryImpl(gameApi, favoriteDao)
    }

}