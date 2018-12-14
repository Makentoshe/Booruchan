package com.makentoshe.repository.cache

import java.lang.IllegalStateException
import java.util.HashMap
import java.util.concurrent.ArrayBlockingQueue

open class CacheImpl<K, V>(size: Int) : Cache<K, V> {

    protected val keysQueue = ArrayBlockingQueue<K>(size)
    protected val storage = HashMap<K, V>()

    override fun get(key: K, loader: () -> V): V {
        var obj = getIfPresent(key)
        if (obj == null) {
            obj = loader()
            try {
                storage[key] = obj
                keysQueue.add(key)
            } catch (e: IllegalStateException) {
                val polledKey = keysQueue.poll()
                remove(polledKey)
                storage[key] = obj
                keysQueue.add(key)
            }
        }
        return obj!!
    }

    override fun getIfPresent(key: K) = storage[key]

    override fun remove(key: K): V? {
        keysQueue.remove(key)
        return storage.remove(key)
    }

    override fun getAll(): Collection<V> = storage.values
}