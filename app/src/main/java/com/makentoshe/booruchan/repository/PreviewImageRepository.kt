package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post

/**
 * Preview images repository which contains a byte arrays as a preview images.
 * The image can be returned by url which is represented by string.
 */
class PreviewImageRepository(private val booru: Booru) : Repository<Post, ByteArray> {

    /**
     * Returns an image from the cache by the key.
     *
     * @param key a post.
     * @return an image which is represented by a [ByteArray].
     */
    override fun get(key: Post) = booru.getCustom().request(key.previewUrl).stream.readBytes()
}