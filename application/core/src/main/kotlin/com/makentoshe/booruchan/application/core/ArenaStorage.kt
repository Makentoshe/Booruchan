package com.makentoshe.booruchan.application.core

/**
 * Performs main storage operation for the arenas
 */
interface ArenaStorage<K, V> {

    /** Fetch result from the storage */
    fun fetch(key: K): Result<V>

    /** Save result to the storage */
    fun carry(key: K, value: Result<V>)
}
