package com.makentoshe.booruchan.feature.boorulist.domain.storage

import kotlinx.coroutines.flow.Flow

interface ApplicationAssetDatastore {

    fun getAllAssetsContent(): Flow<List<String>>
}