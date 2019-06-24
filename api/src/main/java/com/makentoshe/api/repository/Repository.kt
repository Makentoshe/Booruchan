package com.makentoshe.api.repository

import com.makentoshe.api.cache.Cache

interface Repository<K, V> {

    fun get(key: K): V?

    fun wrapCache(cache: Cache<K, V>): Repository<K, V> {
        return RepositoryCache(cache, this)
    }
}
