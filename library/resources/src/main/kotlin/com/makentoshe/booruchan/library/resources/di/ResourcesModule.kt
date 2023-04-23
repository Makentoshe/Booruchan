package com.makentoshe.booruchan.library.resources.di

import android.content.Context
import android.content.res.AssetManager
import com.makentoshe.booruchan.feature.boorulist.domain.storage.ApplicationAssetDatastore
import com.makentoshe.booruchan.library.resources.ApplicationAssetDatastoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ResourcesModule {

    @Binds
    abstract fun bindsApplicationAssetDatastore(
        impl: ApplicationAssetDatastoreImpl,
    ): ApplicationAssetDatastore

    companion object {

        private const val BooruContextDataStoreFilename = "BooruContextDataStore"

        @Singleton
        @Provides
        fun providesAssetManager(@ApplicationContext context: Context): AssetManager {
            return context.assets
        }
    }
}
