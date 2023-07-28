package com.makentoshe.booruchan.di

import com.makentoshe.booruchan.feature.network.KtorNetworkRepository
import com.makentoshe.booruchan.feature.network.NetworkRepository
import com.makentoshe.booruchan.library.logging.LogFingerprint
import com.makentoshe.booruchan.library.logging.logInfo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindsNetworkRepository(impl: KtorNetworkRepository): NetworkRepository

    companion object {
        @Singleton
        @Provides
        fun providesHttpClient(): HttpClient = HttpClient(CIO) {
            install(HttpRedirect) {
                checkHttpMethod = false
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        logInfo("httpclient", LogFingerprint.Network, message)
                    }
                }
            }
        }
    }
}