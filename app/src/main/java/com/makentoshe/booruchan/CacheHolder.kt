package com.makentoshe.booruchan

import android.content.Context
import android.content.ContextWrapper
import com.makentoshe.booruapi.Post
import com.makentoshe.repository.ClearableRepository
import com.makentoshe.repository.Repository
import com.makentoshe.repository.cache.Cache
import com.makentoshe.repository.cache.ClearableCache
import java.io.*

abstract class InternalCache<K, V>(private val context: Context, private val title: String) : ClearableCache<K, V> {

    override fun clear() {
        val contextWrapper = ContextWrapper(context)
        val internalDirectory = contextWrapper.getDir(title, Context.MODE_PRIVATE)

        for (file in internalDirectory.listFiles()) {
            if (!file.isDirectory) {
                println("Remove: ${file.name}")
                file.delete()
            }
        }
    }
}

open class PostInternalCache<V>(
    private val context: Context,
    private val title: String
) : InternalCache<Post, V>(context, title) {

    override fun get(key: Post, loader: () -> V): V {
        return getIfPresent(key) ?: loader().also {
            saveToInternalStorage(key, it)
            println("Create: ${key.id}")
        }
    }

    override fun getIfPresent(key: Post): V? {
        return getFromInternalStorage(key)
    }

    //O(n)
    override fun remove(key: Post): V? {
        val contextWrapper = ContextWrapper(context)
        val internalDirectory = contextWrapper.getDir(title, Context.MODE_PRIVATE)

        for (file in internalDirectory.listFiles()) {
            if (file.name == key.id.toString()) {
                return getIfPresent(Post(id = file.name.toInt())).apply {
                    file.delete()
                }
            }
        }
        return null
    }

    override fun getAll(): Collection<V> {
        val contextWrapper = ContextWrapper(context)
        val internalDirectory = contextWrapper.getDir(title, Context.MODE_PRIVATE)

        val list = ArrayList<V>()

        internalDirectory.listFiles().forEach {
            val data = getIfPresent(Post(id = it.name.toInt())) ?: return@forEach
            list.add(data)
        }

        return list
    }

    private fun saveToInternalStorage(key: Post, value: V) {
        val contextWrapper = ContextWrapper(context)
        val internalDirectory = contextWrapper.getDir(title, Context.MODE_PRIVATE).apply { mkdirs() }
        val file = File(internalDirectory, key.id.toString())

        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(value)
            it.flush()
        }
    }

    private fun getFromInternalStorage(key: Post): V? {
        val contextWrapper = ContextWrapper(context)
        val internalDirectory = contextWrapper.getDir(title, Context.MODE_PRIVATE)
        val file = File(internalDirectory, key.id.toString())

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