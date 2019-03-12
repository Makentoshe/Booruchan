package com.makentoshe.booruchan.repository.cache

import android.content.Context
import com.makentoshe.booruapi.Post
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

open class ImageInternalCache(context: Context, title: String) : InternalCache<Post, ByteArray?>(context, title) {

    override fun get(key: Post, loader: () -> ByteArray?): ByteArray? {
        return getIfPresent(key) ?: loader().also {
            if (it != null) saveToInternalStorage(key, it)
        }
    }

    override fun getIfPresent(key: Post): ByteArray? {
        return getFromInternalStorage(key)
    }

    //O(n) TODO replace by binary search mb?
    override fun remove(key: Post): ByteArray? {
        for (file in mainDirectory.listFiles()) {
            if (file.name == key.id.toString()) {
                return getIfPresent(Post(id = file.name.toInt())).apply {
                    file.delete()
                }
            }
        }
        return null
    }

    override fun getAll(): Collection<ByteArray> {
        val list = ArrayList<ByteArray>()

        mainDirectory.listFiles().forEach {
            val data = getIfPresent(Post(id = it.name.toInt())) ?: return@forEach
            list.add(data)
        }

        return list
    }

    private fun saveToInternalStorage(key: Post, value: ByteArray) {
        val file = File(mainDirectory, key.id.toString())
        FileOutputStream(file).use {
            it.write(value)
            it.flush()
        }
    }

    private fun getFromInternalStorage(key: Post): ByteArray? {
        val file = File(mainDirectory, key.id.toString())

        if (!file.exists()) return null

        if (file.isDirectory) {
            file.delete()
            return null
        }

        FileInputStream(file).use {
            return it.readBytes()
        }
    }

}