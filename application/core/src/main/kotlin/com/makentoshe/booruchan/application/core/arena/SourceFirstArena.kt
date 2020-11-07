package com.makentoshe.booruchan.application.core.arena

import com.makentoshe.booruchan.core.network.Filter

/**
 * Arena tries to load from network source first. If the network connection stable the
 * cache will be overwritten by the new result. If the network connection interrupted
 * and data exists in the cache it will be loaded.
 */
abstract class SourceFirstArena<in F: Filter, T>(private val arenaStorage: ArenaStorage<F, T>): Arena<F, T>() {

    override suspend fun suspendFetch(filter: F): Result<T> {
        try {
            val networkFetch = this.internalSuspendFetch(filter)
            if (networkFetch.isSuccess) {
                arenaStorage.carry(filter, networkFetch.getOrNull()!!)
                return networkFetch
            }

            throw networkFetch.exceptionOrNull()!!
        } catch (exception: Exception) {
            val fetchedResult = arenaStorage.fetch(filter)
            if (fetchedResult.isSuccess) return fetchedResult

            val fetchedException = fetchedResult.exceptionOrNull() as ArenaStorageException
            return Result.failure(fetchedException.initCause(exception))
        }
    }
}