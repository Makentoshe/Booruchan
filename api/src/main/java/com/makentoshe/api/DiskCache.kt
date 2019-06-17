package com.makentoshe.api

import android.util.Log
import com.makentoshe.api.BuildConfig.DEBUG
import java.io.File

/**
 * Cache interface using internal storage.
 *
 * @param directory where files store.
 */
class DiskCache(private val directory: File) : Cache<String, ByteArray> {

    /** Key, Path */
    private val hashMap = LinkedHashMap<String, String>()

    init {
        directory.listFiles().filter { it.isFile }.forEach { hashMap[it.name] = it.path }
    }

    override fun clear() {
        hashMap.clear()
        directory.deleteRecursively()
    }

    override fun get(key: String): ByteArray? = if (hashMap.containsKey(key)) File(hashMap[key]).readBytes() else null

    override fun add(key: String, value: ByteArray) = File(directory, key).let {
        val isCreated = it.createNewFile()
        if (DEBUG) Log.i("Caches", "key=$key\nisCreated=$isCreated")
        it.writeBytes(value)
        hashMap[it.name] = it.path
    }
}