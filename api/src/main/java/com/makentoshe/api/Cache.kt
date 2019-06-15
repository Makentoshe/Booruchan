package com.makentoshe.api

/**
 * Interface describes a simple cache object with Key-Value storage
 */
interface Cache<K, V> {
    /** Get value [V] by the key [K] or null */
    fun get(key: K): V?
    /** Add value [V] associated with the key [K] */
    fun add(key: K, value: V)
    /** Clear all cache */
    fun clear()
}