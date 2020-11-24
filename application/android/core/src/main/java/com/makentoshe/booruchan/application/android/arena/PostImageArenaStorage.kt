package com.makentoshe.booruchan.application.android.arena

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.post.Image
import java.io.File
import java.io.FileInputStream

abstract class PostImageArenaStorage(
    private val cacheRootDirectory: File
) : ArenaStorage<Image, ByteArray> {

    protected abstract val imageCacheDirectoryTitle: String

    override fun fetch(key: Image): Result<ByteArray> = try {
        val cacheDirectory = File(cacheRootDirectory, imageCacheDirectoryTitle)
        val file = File(cacheDirectory, key.name)
        if (!file.exists()) throw Exception()
        Result.success(FileInputStream(file).use { it.readBytes() })
    } catch (exception: Exception) {
        Result.failure(ArenaStorageException("Could not receive a record by key: ${key.name}").initCause(exception))
    }

    override fun carry(key: Image, value: ByteArray) {
        val cacheDirectory = File(cacheRootDirectory, imageCacheDirectoryTitle)
        val file = File(cacheDirectory, key.name)
        if (file.parentFile?.exists() == false) file.parentFile?.mkdirs()
        file.writeBytes(value)
    }
}
