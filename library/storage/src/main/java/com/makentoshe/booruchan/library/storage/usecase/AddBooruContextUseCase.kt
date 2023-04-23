package com.makentoshe.booruchan.library.storage.usecase

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.library.storage.repository.BooruContextRepository
import javax.inject.Inject

class AddBooruContextUseCase @Inject constructor(
    private val repository: BooruContextRepository,
) {
    suspend operator fun invoke(booruContext: BooruContext) {
        return repository.addBooruContext(booruContext)
    }
}

