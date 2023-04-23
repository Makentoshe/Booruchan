package com.makentoshe.booruchan.feature.boorulist.domain.usecase

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooruContextsUseCase @Inject constructor(
    private val repository: BooruContextRepository,
){
    suspend operator fun invoke(): Flow<List<BooruContext>> {
        return repository.getBooruContextList()
    }
}