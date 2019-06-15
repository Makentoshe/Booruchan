package com.makentoshe.api

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
        it.writeBytes(value)
        it.createNewFile()
        hashMap[it.name] = it.path
    }
}