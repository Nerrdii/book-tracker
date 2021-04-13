package com.example.booktracker.di

import com.example.booktracker.api.GoogleBooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGoogleBooksService(): GoogleBooksService {
        return GoogleBooksService.create()
    }

}
