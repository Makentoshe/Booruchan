package com.makentoshe.booruchan.library.storage.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.makentoshe.booruchan.feature.boorulist.domain.storage.BooruContextDatastore
import com.makentoshe.booruchan.library.storage.BooruContextDatastoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StorageModule {

    @Binds
    abstract fun bindsBooruContextDatastore(impl: BooruContextDatastoreImpl): BooruContextDatastore

    companion object {

        private const val BooruContextDataStoreFilename = "BooruContextDataStore"

        @Singleton
        @Provides
        fun providesBooruContextDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile(name = BooruContextDataStoreFilename)
            }
        }
    }
}
