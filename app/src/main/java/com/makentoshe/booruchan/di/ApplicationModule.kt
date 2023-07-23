package com.makentoshe.booruchan.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

//    companion object {
//        @Singleton
//        @Provides
//        fun providesHttpClient(): HttpClient = HttpClient(CIO) {
//            install(HttpRedirect) {
//                checkHttpMethod = false
//            }
//
//            install(Logging) {
//                level = LogLevel.ALL
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        logInfo("httpclient", LogFingerprint.Network, message)
//                    }
//                }
//            }
//        }
//
//        @Singleton
//        @Provides
//        fun providesBooruSources() = listOf(
//            "com.makentoshe.booruchan.extension.gelbooru.GelbooruSource",
//            "com.makentoshe.booruchan.extension.safebooru.SafebooruSource",
//        ).mapNotNull { `package` ->
//            Class.forName(`package`).kotlin.primaryConstructor?.call() as? BooruSource
//        }.let { BooruSources(it) }
//    }
}