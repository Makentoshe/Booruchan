package com.makentoshe.booruchan.repository.cache

import java.io.Serializable

interface Cache<K, V> : Serializable {
    fun get(key: K, loader: () -> V): V
    fun getIfPresent(key: K): V?
    fun remove(key: K): V?
    fun getAll(): Collection<V>
    fun clear()
}