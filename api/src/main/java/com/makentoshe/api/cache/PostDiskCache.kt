package com.makentoshe.api.cache

import android.content.Context
import com.makentoshe.boorulibrary.entitiy.Post
import java.io.*

class PostDiskCache(private val directory: File) : Cache<Long, Post> {

    /** Returns a post by key. If there is no post or something else the null will be returned */
    override fun get(key: Long): Post? {
        val file = File(directory, key.toString())
        // if there is no file in cache
        if (!file.exists()) return null
        // if the selected file is a directory
        if (file.isDirectory) return file.delete().let { null }
        try {
            ObjectInputStream(FileInputStream(file)).use {
                return it.readObject() as Post
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /** Put a post to a cache */
    override fun add(key: Long, value: Post) {
        val file = File(directory, value.id.toString())
        ObjectOutputStream(FileOutputStream(file)).use {
            it.writeObject(value)
            it.flush()
        }
    }

    /** Clear cache */
    override fun clear() {
        directory.deleteRecursively()
        directory.mkdirs()
    }

    companion object {

        /** Returns an absolute path to a cache directory */
        fun getDir(context: Context): File = context.getDir("Posts",
            Context.MODE_PRIVATE)

        /** Creates a default cache uses an internal storage */
        fun build(context: Context) = PostDiskCache(getDir(context))
    }
}