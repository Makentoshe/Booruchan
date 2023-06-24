package com.makentoshe.booruchan.feature.boorulist.domain.usecase

import com.makentoshe.booruchan.feature.context.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooruContextUseCase @Inject constructor(
    private val repository: BooruContextRepository
) {
    suspend operator fun invoke(booruContextUrl: String): Flow<BooruContext> {
        return repository.getBooruContext(booruContextUrl)
    }
}