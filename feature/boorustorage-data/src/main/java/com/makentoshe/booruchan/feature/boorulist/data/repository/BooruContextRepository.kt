package com.makentoshe.booruchan.feature.boorulist.data.repository

import com.makentoshe.booruchan.feature.context.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.storage.BooruContextDatastore
import com.makentoshe.booruchan.feature.boorulist.data.mapper.BooruContext2DatastoredBooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.DatastoredBooruContext2BooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BooruContextRepositoryImpl @Inject internal constructor(
    private val booruContextDatastore: BooruContextDatastore,
    private val datastoredBooryContext2BooruContextMapper: DatastoredBooruContext2BooruContextMapper,
    private val booruContext2DatastoredBooruContextMapper: BooruContext2DatastoredBooruContextMapper,
) : BooruContextRepository {

    override suspend fun getBooruContextList(): Flow<List<BooruContext>> {
        return booruContextDatastore.getBooruContextList().map { datastoredBooruContextList ->
            datastoredBooruContextList.map(datastoredBooryContext2BooruContextMapper::map)
        }
    }

    override suspend fun addBooruContext(booruContext: BooruContext) {
        val datastoredBooruContext = booruContext2DatastoredBooruContextMapper.map(booruContext)
        booruContextDatastore.addBooruContext(datastoredBooruContext)
    }

    override suspend fun getBooruContext(booruContextUrl: String): Flow<BooruContext> {
        return booruContextDatastore.getBooruContext(booruContextUrl).map { datastoredBooruContext ->
            datastoredBooryContext2BooruContextMapper.map(datastoredBooruContext)
        }
    }
}
