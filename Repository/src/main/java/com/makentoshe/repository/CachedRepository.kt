package com.makentoshe.repository

import com.makentoshe.repository.cache.ClearableCache

class CachedRepository<K, V>(
    internal val cache: ClearableCache<K, V?>,
    private val repository: Repository<K, V>
) : ClearableRepository<K, V> {

    override fun get(key: K): V? {
        return cache.get(key) { repository.get(key) }
    }

    override fun clear() = cache.clear()
}

