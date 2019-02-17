package com.makentoshe.booruchan

import android.content.Context
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import java.io.*

class PostInternalCache(context: Context, title: String) : InternalCache<Booru.PostRequest, Posts?>(context, title) {
    override fun get(key: Booru.PostRequest, loader: () -> Posts?): Posts? {
        return getIfPresent(key) ?: loader().also {
            if (it == null) return null
            saveToInternalStorage(key, it)
        }
    }

    override fun getIfPresent(key: Booru.PostRequest): Posts? {
        return getFromInternalStorage(key)
    }

    override fun remove(key: Booru.PostRequest): Posts? {
        val startPosition = key.page * key.count
        val posts = mutableListOf<Post>()
        for (i in startPosition until startPosition + key.count) {
            val file = File(mainDirectory, i.toString())
            if (!file.exists()) continue
            if (file.isDirectory) {
                file.delete()
                continue
            }
            try {
                ObjectInputStream(FileInputStream(file)).use {
                    posts.add(it.readObject() as Post)
                }
            } finally {
                file.delete()
            }
        }
        return Posts(posts)
    }

    override fun getAll(): Collection<Posts> {
        val list = mutableListOf<Post>()
        mainDirectory.listFiles().forEach {
            try {
                ObjectInputStream(FileInputStream(it)).use {
                    list.add(it.readObject() as Post)
                }
            } catch (e: Exception) {
                //do nothing
                e.printStackTrace()
            }
        }
        return listOf(Posts(list))
    }


    private fun saveToInternalStorage(key: Booru.PostRequest, value: Posts) {
        val startPosition = key.page * key.count
        for (i in startPosition until startPosition + key.count) {
            val file = File(mainDirectory, i.toString())
            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeObject(value[i - startPosition])
                it.flush()
            }
        }
    }


    private fun getFromInternalStorage(key: Booru.PostRequest): Posts? {
        val startPosition = key.page * key.count
        val posts = mutableListOf<Post>()
        mainDirectory.listFiles().forEach { print("${it.name} ") }
        for (i in startPosition until startPosition + key.count) {
            val file = File(mainDirectory, i.toString())
            if (!file.exists()) return null
            if (file.isDirectory) {
                file.delete()
                return null
            }
            try {
                ObjectInputStream(FileInputStream(file)).use {
                    posts.add(it.readObject() as Post)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return Posts(posts)
    }
}

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