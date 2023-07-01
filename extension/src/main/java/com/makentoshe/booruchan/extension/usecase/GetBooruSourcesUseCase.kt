package com.makentoshe.booruchan.extension.usecase

import com.makentoshe.booruchan.extension.BooruSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetBooruSourcesUseCase @Inject constructor(
    private val booruSources: BooruSources,
) {
    suspend operator fun invoke(): Flow<List<BooruSource>> {
        return flowOf(booruSources.list)
    }
}

data class BooruSources(val list: List<BooruSource>)