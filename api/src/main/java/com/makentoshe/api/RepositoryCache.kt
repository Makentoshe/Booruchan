package com.makentoshe.api

/**
 * Repository decorator for caching.
 */
class RepositoryCache<K, V>(
    private val cache: Cache<K, V>, private val repository: Repository<K, V>
) : Repository<K, V> {

    override fun get(key: K) = cache.get(key) ?: repository.get(key)?.also { cache.add(key, it) }
}