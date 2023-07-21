package com.makentoshe.booruchan.extension.usecase

import com.makentoshe.booruchan.extension.BooruSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetBooruSourceUseCase @Inject constructor(
    private val booruSources: BooruSources,
) {
    suspend operator fun invoke(booruSourceId: String): Flow<BooruSource> {
        return flowOf(booruSources.list.first { it.id == booruSourceId })
    }
}