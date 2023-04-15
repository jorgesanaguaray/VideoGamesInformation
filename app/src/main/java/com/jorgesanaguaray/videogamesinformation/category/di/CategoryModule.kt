package com.jorgesanaguaray.videogamesinformation.category.di

import com.jorgesanaguaray.videogamesinformation.category.data.CategoryRepositoryImpl
import com.jorgesanaguaray.videogamesinformation.category.domain.CategoryRepository
import com.jorgesanaguaray.videogamesinformation.core.data.remote.GameApi
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
object CategoryModule {

    @Singleton
    @Provides
    fun provideRepository(gameApi: GameApi): CategoryRepository {
        return CategoryRepositoryImpl(gameApi)
    }

}