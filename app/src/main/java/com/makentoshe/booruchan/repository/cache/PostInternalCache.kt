package com.makentoshe.booruchan.repository.cache

import android.content.Context
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.InternalCache
import java.io.*

class PostInternalCache(context: Context) : InternalCache<Booru.PostRequest, List<Post>?>(context, "posts") {
    override fun get(key: Booru.PostRequest, loader: () -> List<Post>?): List<Post>? {
        return getIfPresent(key) ?: loader().also {
            if (it == null) return null
            saveToInternalStorage(key, it)
        }
    }

    override fun getIfPresent(key: Booru.PostRequest): List<Post>? {
        return getFromInternalStorage(key)
    }

    override fun remove(key: Booru.PostRequest): List<Post>? {
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

    override fun getAll(): Collection<List<Post>> {
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


    private fun saveToInternalStorage(key: Booru.PostRequest, value: List<Post>) {
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