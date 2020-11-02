package com.makentoshe.booruchan.application.core

import com.makentoshe.booruchan.core.network.Filter

/**
 * Performs main network operations with the data and caches a result into the [arenaStorage]
 *
 * If the network connection stable the cache will be overwritten by the new result.
 * If the network connection interrupted and exists in the cache the data will be loaded.
 *
 * TODO: Add comparing the first data in the cache and from network.
 * NOTE: This allows to understand - should be cache updated or it is ok
 * NOTE: Also good point to check and perform offset if first compare is ok,
 * but the next calls will be with the repeated items, you know,
 * when new posts were added to the head.
 * */
abstract class Arena<in F : Filter, T>(private val arenaStorage: ArenaStorage<F, T>) {

    /** Performs main network operation */
    internal abstract suspend fun internalSuspendFetch(filter: F): Result<T>

    suspend fun suspendFetch(filter: F): Result<T> = try {
        this.internalSuspendFetch(filter).apply { arenaStorage.carry(filter, this) }
    } catch (exception: Exception) {
        arenaStorage.fetch(filter)
    }
}