package com.makentoshe.booruchan

import android.content.Context
import com.makentoshe.booruapi.Post
import java.io.*

open class PreviewsInternalCache<V>(context: Context, title: String) : InternalCache<Post, V>(context, title) {

    override fun get(key: Post, loader: () -> V): V {
        return getIfPresent(key) ?: loader().also {
            saveToInternalStorage(key, it)
        }
    }

    override fun getIfPresent(key: Post): V? {
        return getFromInternalStorage(key)
    }

    //O(n)
    override fun remove(key: Post): V? {
        for (file in mainDirectory.listFiles()) {
            if (file.name == key.id.toString()) {
                return getIfPresent(Post(id = file.name.toInt())).apply {
                    file.delete()
                }
            }
        }
        return null
    }

    override fun getAll(): Collection<V> {
        val list = ArrayList<V>()

        mainDirectory.listFiles().forEach {
            val data = getIfPresent(Post(id = it.name.toInt())) ?: return@forEach
            list.add(data)
        }

        return list
    }

    private fun saveToInternalStorage(key: Post, value: V) {
        val file = File(mainDirectory, key.id.toString())

        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(value)
            it.flush()
        }
    }

    private fun getFromInternalStorage(key: Post): V? {
        val file = File(mainDirectory, key.id.toString())

        if (!file.exists()) return null

        if (file.isDirectory) {
            file.delete()
            return null
        }

        ObjectInputStream(FileInputStream(file)).use {
            return it.readObject() as V
        }
    }

}