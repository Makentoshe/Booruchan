package com.makentoshe.booruchan.application.core.arena

import com.makentoshe.booruchan.core.network.Filter

/**
 * Arena tries to load from network source first. If the network connection stable the
 * cache will be overwritten by the new result. If the network connection interrupted
 * and data exists in the cache it will be loaded.
 */
abstract class SourceFirstArena<in K, V>(private val arenaStorage: ArenaStorage<K, V>) : Arena<K, V>() {

    override suspend fun suspendFetch(key: K): Result<V> {
        try {
            val networkFetch = this.internalSuspendFetch(key)
            if (networkFetch.isSuccess) {
                arenaStorage.carry(key, networkFetch.getOrNull()!!)
                return networkFetch
            }

            throw networkFetch.exceptionOrNull()!!
        } catch (exception: Exception) {
            val fetchedResult = arenaStorage.fetch(key)
            if (fetchedResult.isSuccess) return fetchedResult

            val fetchedException = fetchedResult.exceptionOrNull() as ArenaStorageException
            return Result.failure(fetchedException.initCause(exception))
        }
    }
}