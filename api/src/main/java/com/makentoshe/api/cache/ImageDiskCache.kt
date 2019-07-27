package com.makentoshe.api.cache

import android.content.Context
import android.util.Log
import com.makentoshe.api.BuildConfig.DEBUG
import com.makentoshe.boorulibrary.entitiy.Post
import java.io.File

/**
 * Class for caching image as a [ByteArray] using a [Post] as a key.
 *
 * @param directory where files will be stored
 * @param titleStrategy used for a files naming
 */
class ImageDiskCache(
    private val directory: File,
    private val titleStrategy: (Post) -> String = { it.id.toString() }
) : Cache<Post, ByteArray> {

    /** Key, Path */
    private val hashMap = LinkedHashMap<String, String>()

    init {
        // create empty directory if it does not exists
        if (directory.exists().not()) directory.mkdirs()
        // scan all files in directory
        directory.listFiles().orEmpty().filter { it.isFile }.forEach { hashMap[it.name] = it.path }
    }

    override fun get(key: Post): ByteArray? {
        return titleStrategy(key).let { key -> if (hashMap.containsKey(key)) File(hashMap[key]).readBytes() else null }
    }

    override fun clear() {
        hashMap.clear()
        // recreate empty directory
        directory.deleteRecursively()
        directory.mkdirs()
    }

    override fun add(key: Post, value: ByteArray) = File(directory, titleStrategy(key)).let {
        val isCreated = it.createNewFile()
        if (DEBUG) Log.i("Caches", "key=$key\nisCreated=$isCreated")
        it.writeBytes(value)
        hashMap[it.name] = it.path
    }

    companion object {
        /** Returns a cache directory for the preview images */
        fun getPreviewDir(context: Context): File = context.getDir("Preview", Context.MODE_PRIVATE)

        /** Returns a cache instance for the preview images */
        fun getPreviewCache(context: Context): Cache<Post, ByteArray> = ImageDiskCache(getPreviewDir(context))

        /** Returns a cache directory for the sample images */
        fun getSampleDir(context: Context): File = context.getDir("Sample", Context.MODE_PRIVATE)

        /** Returns a cache instance for the sample images */
        fun getSampleCache(context: Context): Cache<Post, ByteArray> = ImageDiskCache(getSampleDir(context))

        /** Returns a cache directory for the full size images */
        fun getFileDir(context: Context): File = context.getDir("File", Context.MODE_PRIVATE)

        /** Returns a cache instance for the fill size images */
        fun getFileCache(context: Context): Cache<Post, ByteArray> = ImageDiskCache(getFileDir(context))
    }
}
