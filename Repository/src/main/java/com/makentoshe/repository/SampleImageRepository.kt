package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.repository.cache.Cache
import java.io.Serializable

/**
 * Sample images repository which contains a byte arrays as a sample images.
 * The image can be returned by url which is represented by string.
 */
class SampleImageRepository(private val booru: Booru) : Repository<Post, ByteArray>, Serializable {

    /**
     * Returns an image from the cache by the key.
     *
     * @param key an image url.
     * @return an image which is represented by a [ByteArray].
     */
    override fun get(key: Post): ByteArray {
        return booru.getPreview(key.sampleUrl).readBytes()
    }
}