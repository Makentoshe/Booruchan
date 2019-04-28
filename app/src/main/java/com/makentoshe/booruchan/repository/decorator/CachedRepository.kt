package com.makentoshe.booruchan.repository.decorator

import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.cache.Cache


class CachedRepository<K, V>(
    val cache: Cache<K, V?>,
    private val repository: Repository<K, V>
) : Repository<K, V> {

    override fun get(key: K): V? {
        return cache.get(key) { repository.get(key) }
    }
}

