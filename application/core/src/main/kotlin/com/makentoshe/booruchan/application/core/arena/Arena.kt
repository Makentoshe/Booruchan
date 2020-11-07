package com.makentoshe.booruchan.application.core.arena

import com.makentoshe.booruchan.core.network.Filter

/**
 * Performs main operations with the data using network and cache sources
 */
abstract class Arena<in F : Filter, T> {

    /** Main method for performing network operation */
    internal abstract suspend fun internalSuspendFetch(filter: F): Result<T>

    /** Method executes network and caches operations */
    abstract suspend fun suspendFetch(filter: F): Result<T>
}