package com.makentoshe.booruchan.feature.boorulist.domain.repository

import kotlinx.coroutines.flow.Flow

interface ApplicationAssetRepository {

    fun getAllAssetsContent(): Flow<List<String>>
}
