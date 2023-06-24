package com.makentoshe.booruchan.repository.cache

interface ClearableCache<K, V> : Cache<K, V> {
    fun clear()
}