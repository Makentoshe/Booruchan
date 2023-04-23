package com.makentoshe.booruchan.library.storage.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.library.storage.domain.DatastoredBooruContext
import com.makentoshe.booruchan.library.storage.mapper.BooruContext2DatastoredBooruContextMapper
import com.makentoshe.booruchan.library.storage.mapper.DatastoredBooruContext2BooruContextMapper
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface BooruContextRepository {
    suspend fun getBooruContextList(): Flow<List<BooruContext>>

    suspend fun addBooruContext(booruContext: BooruContext)
}

internal class BooruContextRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val datastoredBooryContext2BooruContextMapper: DatastoredBooruContext2BooruContextMapper,
    private val booruContext2DatastoredBooruContextMapper: BooruContext2DatastoredBooruContextMapper,
) : BooruContextRepository {

    override suspend fun getBooruContextList(): Flow<List<BooruContext>> {
        return dataStore.data.mapNotNull { preferences ->
            preferences[BOORULIST_IDENTIFIERS]?.mapNotNull { identifier ->
                preferences[stringPreferencesKey(identifier)]
            }?.map { Json.decodeFromString<DatastoredBooruContext>(it) }
        }.map { dataStoredBooruContextList ->
            dataStoredBooruContextList.map(datastoredBooryContext2BooruContextMapper::map)
        }
    }

    override suspend fun addBooruContext(booruContext: BooruContext) {
        val datastoredBooruContext = booruContext2DatastoredBooruContextMapper.map(booruContext)

        dataStore.edit { preferences ->

            val identifiers = preferences[BOORULIST_IDENTIFIERS]
            val identifier = datastoredBooruContext.host

            if (identifiers?.contains(identifier) == true) {
                throw BooruContextRepositoryException.IdentifierAlreadyExists
            }

            preferences[BOORULIST_IDENTIFIERS] = identifiers?.plus(identifier) ?: setOf(identifier)
            preferences[stringPreferencesKey(identifier)] = Json.encodeToString(datastoredBooruContext)
        }
    }

    companion object {
        private val BOORULIST_IDENTIFIERS = stringSetPreferencesKey("AllBoorulistIdentifiers")
    }
}

sealed class BooruContextRepositoryException : Exception() {
    object IdentifierAlreadyExists : BooruContextRepositoryException()
}
