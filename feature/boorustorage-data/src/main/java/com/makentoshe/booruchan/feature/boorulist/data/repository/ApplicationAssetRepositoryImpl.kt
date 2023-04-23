package com.makentoshe.booruchan.feature.boorulist.data.repository

import com.makentoshe.booruchan.feature.boorulist.domain.repository.ApplicationAssetRepository
import com.makentoshe.booruchan.feature.boorulist.domain.storage.ApplicationAssetDatastore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplicationAssetRepositoryImpl @Inject constructor(
    private val datastore: ApplicationAssetDatastore
): ApplicationAssetRepository {

    override fun getAllAssetsContent(): Flow<List<String>> {
        return datastore.getAllAssetsContent()
    }
}