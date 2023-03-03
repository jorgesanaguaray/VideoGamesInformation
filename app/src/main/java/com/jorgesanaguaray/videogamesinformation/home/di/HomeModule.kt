package com.jorgesanaguaray.videogamesinformation.home.di

import com.jorgesanaguaray.videogamesinformation.core.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.home.data.HomeRepositoryImpl
import com.jorgesanaguaray.videogamesinformation.home.domain.HomeRepository
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
object HomeModule {

    @Singleton
    @Provides
    fun provideRepository(gameApi: GameApi): HomeRepository {
        return HomeRepositoryImpl(gameApi)
    }

}