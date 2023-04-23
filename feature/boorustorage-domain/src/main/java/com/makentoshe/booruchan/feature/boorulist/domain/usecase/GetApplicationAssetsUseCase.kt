package com.makentoshe.booruchan.feature.boorulist.domain.usecase

import com.makentoshe.booruchan.feature.boorulist.domain.repository.ApplicationAssetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetApplicationAssetsUseCase @Inject constructor(
    private val repository: ApplicationAssetRepository,
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getAllAssetsContent()
    }
}
