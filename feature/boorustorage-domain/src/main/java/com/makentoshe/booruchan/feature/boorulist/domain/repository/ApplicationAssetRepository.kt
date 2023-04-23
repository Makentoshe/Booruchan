package com.makentoshe.booruchan.feature.boorulist.domain.repository

import com.makentoshe.booruchan.feature.BooruContext
import kotlinx.coroutines.flow.Flow

interface ApplicationAssetRepository {

    fun getAllAssetsContent(): Flow<List<String>>
}
