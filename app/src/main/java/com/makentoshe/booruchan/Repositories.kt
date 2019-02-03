package com.makentoshe.booruchan

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.repository.Repository
import com.makentoshe.repository.cache.Cache
import com.makentoshe.repository.cache.CacheImpl
import java.io.Serializable

/**
 * Default image repository which contains byte arrays as an images.
 */
abstract class ImageRepository : Repository<String, ByteArray>, Serializable

/**
 * Image repository for previews.
 */
class PreviewImageRepository(
    private val booru: Booru,
    private val cache: Cache<String, ByteArray>
) : ImageRepository() {

    override fun get(key: String): ByteArray {
        return cache.get(key) { booru.getPreview(key).readBytes() }
    }
}

/**
 * Image repository for samples.
 */
class SampleImageRepository(
    private val booru: Booru,
    private val cache: Cache<String, ByteArray>
) : ImageRepository() {

    override fun get(key: String): ByteArray {
        return cache.get(key) { booru.getPreview(key).readBytes() }
    }
}

/**
 * Repository contains Posts and returns by "key" - page
 */
class PostsRepository(
    private val booru: Booru,
    private val cache: Cache<Int, Posts>,
    val count: Int,
    val tags: Set<Tag>
) : Repository<Int, Posts>, Serializable {

    override fun get(key: Int): Posts {
        return cache.get(key) { booru.getPosts(count, key, tags) }
    }
}
/**
 * Repository does not contains image files in the memory -
 * just download the file and return as a byte array
 */
class FileImageRepository(private val booru: Booru) : ImageRepository(), Serializable {
    override fun get(key: String) = booru.getPreview(key).readBytes()
}