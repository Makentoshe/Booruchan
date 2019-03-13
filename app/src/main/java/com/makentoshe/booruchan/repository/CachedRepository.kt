package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.repository.cache.ClearableCache


class CachedRepository<K, V>(
    internal val cache: ClearableCache<K, V?>,
    private val repository: Repository<K, V>
) : ClearableRepository<K, V> {

    override fun get(key: K): V? {
        return cache.get(key) { repository.get(key) }
    }

    override fun clear() = cache.clear()
}

