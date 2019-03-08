package com.makentoshe.booruchan.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.repository.cache.Cache

/**
 * Preview images repository which contains a byte arrays as a preview images.
 * The image can be returned by url which is represented by string.
 */
class PreviewImageRepository(private val booru: Booru) : Repository<Post, ByteArray> {

    /**
     * Returns an image from the cache by the key.
     *
     * @param key an image url.
     * @return an image which is represented by a [ByteArray].
     */
    override fun get(key: Post) = booru.getPreview(key.previewUrl).readBytes()
}