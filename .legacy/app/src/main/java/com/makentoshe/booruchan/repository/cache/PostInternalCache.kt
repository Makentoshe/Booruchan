package com.makentoshe.booruchan.repository.cache

import android.content.Context
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.Posts
import java.io.*

class PostInternalCache(context: Context) : InternalCache<Posts.Request, List<Post>?>(context, Type.POST) {
    override fun get(key: Posts.Request, loader: () -> List<Post>?): List<Post>? {
        return getIfPresent(key) ?: loader().also {
            if (it == null) return null
            saveToInternalStorage(key, it)
        }
    }

    override fun getIfPresent(key: Posts.Request): List<Post>? {
        return getFromInternalStorage(key)
    }

    override fun remove(key: Posts.Request): List<Post>? {
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
        return posts
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
        return listOf(list)
    }


    private fun saveToInternalStorage(key: Posts.Request, value: List<Post>) {
        val startPosition = key.page * key.count
        for (i in startPosition until startPosition + key.count) {
            val file = File(mainDirectory, i.toString())
            ObjectOutputStream(FileOutputStream(file)).use {
                try {
                    it.writeObject(value[i - startPosition])
                    it.flush()
                } catch (e: IndexOutOfBoundsException) {
                    //its ok - caused when we request 12 items, but receive 8, for example
                }
            }
        }
    }


    private fun getFromInternalStorage(key: Posts.Request): List<Post>? {
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
        return posts
    }
}