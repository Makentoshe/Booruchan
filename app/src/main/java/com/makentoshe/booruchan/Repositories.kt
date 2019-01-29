package com.makentoshe.booruchan

import com.makentoshe.booruapi.Booru
import com.makentoshe.repository.Repository
import com.makentoshe.repository.cache.Cache
import java.io.Serializable

/**
 * Default image repository which contains byte arrays as an images.
 */
abstract class ImageRepository: Repository<String, ByteArray>, Serializable

/**
 * Image repository for samples.
 */
class SampleImageRepository(
    private val booru: Booru,
    private val cache: Cache<String, ByteArray>
): ImageRepository() {

    override fun get(key: String): ByteArray {
        return cache.get(key) { booru.getPreview(key).readBytes() }
    }
}