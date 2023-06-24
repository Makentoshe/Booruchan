package com.makentoshe.booruchan.application.core.arena

import com.makentoshe.booruchan.core.network.Filter

/**
 * Performs main operations with the data using network and cache sources
 */
abstract class Arena<in K, V> {

    /** Main method for performing network operation */
    internal abstract suspend fun internalSuspendFetch(key: K): Result<V>

    /** Method executes network and caches operations */
    abstract suspend fun suspendFetch(key: K): Result<V>
}