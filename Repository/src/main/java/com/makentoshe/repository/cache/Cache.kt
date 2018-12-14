package com.makentoshe.repository.cache

interface Cache<K, V> {
    fun get(key: K, loader: () -> V): V
    fun getIfPresent(key: K): V?
    fun remove(key: K): V?
    fun getAll(): Collection<V>
}

