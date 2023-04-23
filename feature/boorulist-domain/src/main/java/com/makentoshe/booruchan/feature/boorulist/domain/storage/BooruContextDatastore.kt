package com.makentoshe.booruchan.feature.boorulist.domain.storage

import com.makentoshe.booruchan.feature.boorulist.domain.DatastoredBooruContext
import kotlinx.coroutines.flow.Flow

interface BooruContextDatastore {

    fun getBooruContextList(): Flow<List<DatastoredBooruContext>>

    suspend fun addBooruContext(datastoredBooruContext: DatastoredBooruContext)
}