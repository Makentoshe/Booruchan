package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.post.Image
import java.io.File
import java.io.FileInputStream

class PostPreviewArenaStorage(
    private val cacheRootDirectory: File
) : ArenaStorage<Image, ByteArray> {

    companion object {
        private const val previewCacheDirectoryTitle = "preview"
    }

    override fun fetch(key: Image): Result<ByteArray> = try {
        val cacheDirectory = File(cacheRootDirectory, previewCacheDirectoryTitle)
        val file = File(cacheDirectory, key.name)
        println("Load: ${file.absolutePath}")
        if (!file.exists()) throw Exception()
        Result.success(FileInputStream(file).use { it.readBytes() })
    } catch (exception: Exception) {
        Result.failure(ArenaStorageException("Could not receive a record by key: ${key.name}", exception))
    }

    override fun carry(key: Image, value: ByteArray) {
        val cacheDirectory = File(cacheRootDirectory, previewCacheDirectoryTitle)
        val file = File(cacheDirectory, key.name)
        println("Save: ${file.absolutePath}")
        if (file.parentFile?.exists() == false) file.parentFile?.mkdirs()
        file.writeBytes(value)
    }
}