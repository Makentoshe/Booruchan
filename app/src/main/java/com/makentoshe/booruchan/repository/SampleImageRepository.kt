package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post

class SampleImageRepository(private val booru: Booru) : Repository<Post, ByteArray> {

    /**
     * Returns an image from the cache by the key.
     *
     * @param key a post.
     * @return an image which is represented by a [ByteArray].
     */
    override fun get(key: Post): ByteArray {
        return booru.getCustom().request(key.sampleUrl).stream.readBytes()
    }
}