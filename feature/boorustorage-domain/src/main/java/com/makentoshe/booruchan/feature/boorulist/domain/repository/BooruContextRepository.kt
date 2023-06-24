package com.makentoshe.booruchan.feature.boorulist.domain.repository

import com.makentoshe.booruchan.feature.context.BooruContext
import kotlinx.coroutines.flow.Flow

interface BooruContextRepository {
    suspend fun getBooruContextList(): Flow<List<BooruContext>>

    suspend fun addBooruContext(booruContext: BooruContext)

    suspend fun getBooruContext(booruContextUrl: String): Flow<BooruContext>
}
