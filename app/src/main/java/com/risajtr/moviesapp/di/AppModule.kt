package com.risajtr.moviesapp.di

import android.content.Context
import com.risajtr.moviesapp.data.DataSource
import com.risajtr.moviesapp.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * For Hilt injection.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides DataSource instance
    @Provides
    @Singleton
    fun provideDataSource(@ApplicationContext context: Context): DataSource {
        return DataSource(context)
    }

    // Provides MoviesRepository instance
    @Provides
    @Singleton
    fun provideMoviesRepository(dataSource: DataSource): MoviesRepository {
        return MoviesRepository(dataSource = dataSource)
    }

}