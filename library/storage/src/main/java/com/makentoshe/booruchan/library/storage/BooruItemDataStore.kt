package com.makentoshe.booruchan.library.storage

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.library.storage.repository.BooruContextRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooruContextsUseCase @Inject constructor(
    private val repository: BooruContextRepository,
){
    suspend operator fun invoke(): Flow<List<BooruContext>> {
        return repository.getBooruContextList()
    }
}

class AddBooruContextUseCase @Inject constructor(
    private val repository: BooruContextRepository,
) {
    suspend operator fun invoke(booruContext: BooruContext) {
        return repository.addBooruContext(booruContext)
    }
}

