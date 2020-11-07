package com.makentoshe.booruchan.application.core.arena

/**
 * Arena tries to get data from storage first. If storage does not contains element by the key [K]
 * the data will be fetched from the source and save in the storage for the future reuse.
 */
abstract class StorageFirstArena<in K, V>(private val arenaStorage: ArenaStorage<K, V>) : Arena<K, V>() {
    override suspend fun suspendFetch(key: K): Result<V> = try {
        val storageFetch = arenaStorage.fetch(key)
        if (storageFetch.isSuccess) {
            storageFetch
        } else {
            throw storageFetch.exceptionOrNull()!!
        }
    } catch (exception: Exception) {
        val sourceFetch = internalSuspendFetch(key)
        if (sourceFetch.isSuccess) {
            arenaStorage.carry(key, sourceFetch.getOrNull()!!)
            sourceFetch
        } else {
            Result.failure(sourceFetch.exceptionOrNull()!!.initCause(exception))
        }
    }
}