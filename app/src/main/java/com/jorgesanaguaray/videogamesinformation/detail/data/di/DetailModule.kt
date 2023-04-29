package com.jorgesanaguaray.videogamesinformation.detail.data.di

import com.jorgesanaguaray.videogamesinformation.core.data.remote.GameApi
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
    fun provideRepository(gameApi: GameApi): DetailRepository {
        return DetailRepositoryImpl(gameApi)
    }

}