package com.makentoshe.booruchan.feature.boorulist.domain.usecase

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.DatastoredBooruContext2BooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.mapper.String2DatastoredBooruContextMapper
import com.makentoshe.booruchan.feature.boorulist.domain.repository.ApplicationAssetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBooruContextAssetsUseCase @Inject constructor(
    private val repository: ApplicationAssetRepository,
    private val string2DatastoredBooruContextMapper: String2DatastoredBooruContextMapper,
    private val datastoredBooruContext2BooruContextMapper: DatastoredBooruContext2BooruContextMapper,
) {
    operator fun invoke(): Flow<List<BooruContext>> {
        return repository.getAllAssetsContent().map { assets ->
            assets.map { asset ->
                string2DatastoredBooruContextMapper.map(asset)
            }.map {
                datastoredBooruContext2BooruContextMapper.map(it)
            }
        }
    }
}