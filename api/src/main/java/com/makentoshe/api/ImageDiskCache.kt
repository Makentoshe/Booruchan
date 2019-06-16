package com.makentoshe.api

import android.content.Context
import com.makentoshe.boorulibrary.entitiy.Post
import java.io.File

/**
 * Class for caching image as a [ByteArray] using a [Post] as a key.
 */
class ImageDiskCache(private val diskCache: DiskCache) : Cache<Post, ByteArray> {

    override fun get(key: Post) = diskCache.get(key.id.toString())

    override fun add(key: Post, value: ByteArray) = diskCache.add(key.id.toString(), value)

    override fun clear() = diskCache.clear()

    companion object {
        /** Returns a cache directory for the preview images */
        fun getPreviewDir(context: Context): File = context.getDir("Preview", Context.MODE_PRIVATE)
    }
}