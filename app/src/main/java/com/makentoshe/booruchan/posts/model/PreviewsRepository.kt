package com.makentoshe.booruchan.posts.model

import com.makentoshe.booruapi.Booru
import com.makentoshe.repository.Repository
import com.makentoshe.repository.cache.Cache
import java.io.Serializable

class PreviewsRepository(
    private val booru: Booru,
    private val cache: Cache<String, ByteArray>
) : Repository<String, ByteArray>, Serializable {
    override fun get(key: String): ByteArray? {
        return cache.get(key) { booru.getPreview(key).readBytes() }
    }
}
