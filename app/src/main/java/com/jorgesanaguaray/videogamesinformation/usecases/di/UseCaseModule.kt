package com.jorgesanaguaray.videogamesinformation.usecases.di

import com.jorgesanaguaray.videogamesinformation.core.local.FavoriteDao
import com.jorgesanaguaray.videogamesinformation.usecases.domain.UseCaseRepository
import com.jorgesanaguaray.videogamesinformation.usecases.data.UseCaseRepositoryImpl
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
object UseCaseModule {

    @Singleton
    @Provides
    fun provideRepository(favoriteDao: FavoriteDao): UseCaseRepository {
        return UseCaseRepositoryImpl(favoriteDao)
    }

}