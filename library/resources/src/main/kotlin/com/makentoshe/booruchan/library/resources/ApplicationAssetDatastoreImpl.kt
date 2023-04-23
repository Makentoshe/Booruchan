package com.makentoshe.booruchan.library.resources

import android.content.res.AssetManager
import com.makentoshe.booruchan.feature.boorulist.domain.storage.ApplicationAssetDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import javax.inject.Inject

class ApplicationAssetDatastoreImpl @Inject constructor(
    private val assetManager: AssetManager,
) : ApplicationAssetDatastore {

    override fun getAllAssetsContent(): Flow<List<String>> {
        return flowOf(assetManager.list(ASSET_PATH)?.mapNotNull { assetPath ->
            val filename = ASSET_PATH + File.separator + assetPath
            String(assetManager.open(filename).readBytes())
        } ?: emptyList())
    }

    companion object {
        private const val ASSET_PATH = "boorulist"
    }
}