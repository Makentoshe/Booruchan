package com.makentoshe.booruchan.feature.boorulist.domain.usecase

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.repository.BooruContextRepository
import javax.inject.Inject

class AddBooruContextUseCase @Inject constructor(
    private val repository: BooruContextRepository,
) {
    suspend operator fun invoke(booruContext: BooruContext) {
        return repository.addBooruContext(booruContext)
    }
}

