package com.makentoshe.booruchan.application.android.arena

import android.annotation.SuppressLint
import android.util.Log
import com.makentoshe.booruchan.application.android.database.dao.SampleContentDao
import com.makentoshe.booruchan.application.android.database.record.SampleContentRecord
import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.post.Content
import java.io.File
import java.io.FileNotFoundException

class SampleContentArenaCache(
    private val sampleContentDao: SampleContentDao,
    private val cacheRootDirectory: File
) : ArenaStorage<Content, ByteArray> {

    companion object {
        @SuppressLint("LongLogTag")
        private fun capture(level: Int, message: () -> String) {
            Log.println(level, "SampleContentArenaStorage", message())
        }
    }

    private val limit = 100
    private val step = 20
    private val directory = "sample"

    override fun fetch(key: Content): Result<ByteArray> = try {
        Result.success(fileSystemFetch(key) ?: throw FileNotFoundException())
    } catch (exception: Exception) {
        Result.failure(ArenaStorageException("Could not receive a record by key: ${key.name}").initCause(exception))
    }

    private fun fileSystemFetch(key: Content): ByteArray? {
        val cacheDirectory = File(cacheRootDirectory, directory)
        val file = File(cacheDirectory, key.name)
        return if (!file.exists()) null else file.readBytes().also {
            capture(Log.INFO) { "Fetch sample from file system with path ${file.relativeTo(cacheDirectory).path}" }
        }
    }

    override fun carry(key: Content, value: ByteArray) {
        if (sampleContentDao.count() > limit) {
            capture(Log.INFO) { "Removing oldest $step elements from cache" }
            sampleContentDao.getLast(step).forEach { record ->
                sampleContentDao.delete(record)
                File(cacheRootDirectory, record.location).delete()
            }
        }

        val sampleFile = fileSystemCarry(key, value)
        sampleContentDao.insert(SampleContentRecord(sampleFile.relativeTo(cacheRootDirectory).path, key))
        capture(Log.INFO) {
            "Store sample in file system with path ${sampleFile.relativeTo(cacheRootDirectory).path}"
        }
    }

    private fun fileSystemCarry(key: Content, value: ByteArray): File {
        val cacheDirectory = File(cacheRootDirectory, directory)
        return File(cacheDirectory, key.name).apply {
            if (!cacheDirectory.exists()) cacheDirectory.mkdirs()
            writeBytes(value)
        }
    }
}
