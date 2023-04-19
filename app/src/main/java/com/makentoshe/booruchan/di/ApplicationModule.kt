package com.makentoshe.booruchan.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providesHttpClient(): HttpClient = HttpClient(CIO) {
        install(HttpRedirect) {
            checkHttpMethod = false
        }
    }

}