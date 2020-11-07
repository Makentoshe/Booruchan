package com.makentoshe.booruchan.application.core.arena

/**
 * Arena tries to get data from storage first. If storage does not contains element by the key [F]
 * the data will be fetched from the source and save in the storage for the future reuse.
 */
abstract class StorageFirstArena<in F : Filter, T>(private val arenaStorage: ArenaStorage<F, T>) : Arena<F, T>() {
    override suspend fun suspendFetch(filter: F): Result<T> = try {
        val storageFetch = arenaStorage.fetch(filter)
        if (storageFetch.isSuccess) {
            storageFetch
        } else {
            throw storageFetch.exceptionOrNull()!!
        }
    } catch (exception: Exception) {
        val sourceFetch = internalSuspendFetch(filter)
        if (sourceFetch.isSuccess) {
            arenaStorage.carry(filter, sourceFetch.getOrNull()!!)
            sourceFetch
        } else {
            Result.failure(sourceFetch.exceptionOrNull()!!.initCause(exception))
        }
    }
}