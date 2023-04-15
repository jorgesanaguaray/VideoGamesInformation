package com.jorgesanaguaray.videogamesinformation.search.di

import com.jorgesanaguaray.videogamesinformation.core.data.remote.GameApi
import com.jorgesanaguaray.videogamesinformation.search.data.SearchRepositoryImpl
import com.jorgesanaguaray.videogamesinformation.search.domain.SearchRepository
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
object SearchModule {

    @Singleton
    @Provides
    fun provideRepository(gameApi: GameApi): SearchRepository {
        return SearchRepositoryImpl(gameApi)
    }

}