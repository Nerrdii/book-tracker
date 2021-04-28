package com.example.booktracker.di

import com.example.booktracker.api.GoogleBooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Similar to DatabaseModule, this class provides a Singleton instance of the Google Books API
// service to the application to use with dependency injection

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGoogleBooksService(): GoogleBooksService {
        return GoogleBooksService.create()
    }

}
