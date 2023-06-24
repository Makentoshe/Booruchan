package com.makentoshe.booruchan.application.core.arena

/**
 * Performs main storage operation for the arenas
 */
interface ArenaStorage<K, V> {

    /**
     * Fetch result from the storage.
     * If could not return record by the key - returns [Result] with the [ArenaStorageException]
     */
    fun fetch(key: K): Result<V>

    /**
     * Save result to the storage.
     */
    fun carry(key: K, value: V)
}
