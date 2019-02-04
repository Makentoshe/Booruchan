package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.repository.cache.Cache

/**
 * Preview images repository which contains a byte arrays as a preview images.
 * The image can be returned by url which is represented by string.
 */
class PreviewImageRepository(
    private val booru: Booru,
    private val cache: Cache<String, ByteArray>
) : ImageRepository() {

    /**
     * Returns an image from the cache by the key.
     *
     * @param key an image url.
     * @return an image which is represented by a [ByteArray].
     */
    override fun get(key: String): ByteArray {
        return cache.get(key) { booru.getPreview(key).readBytes() }
    }
}