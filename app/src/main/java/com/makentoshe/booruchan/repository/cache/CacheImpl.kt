package com.makentoshe.booruchan.repository.cache

import java.io.Serializable
import java.lang.IllegalStateException
import java.util.HashMap
import java.util.concurrent.ArrayBlockingQueue

open class CacheImpl<K, V> internal constructor(size: Int) : Cache<K, V>, Serializable {

    protected val keysQueue = ArrayBlockingQueue<K>(size)
    protected val storage = HashMap<K, V>()

    override fun get(key: K, loader: () -> V): V {
        var obj = getIfPresent(key)
        if (obj == null) {
            obj = loader()
            addObjToStorage(key, obj)
        }
        return obj!!
    }

    private fun addObjToStorage(key: K, obj: V) {
        try {
            storage[key] = obj
            keysQueue.add(key)
        } catch (e: IllegalStateException) {
            val polledKey = keysQueue.poll()
            remove(polledKey)
            addObjToStorage(key, obj)
        }
    }

    override fun getIfPresent(key: K) = storage[key]

    override fun remove(key: K): V? {
        keysQueue.remove(key)
        return storage.remove(key)
    }

    override fun getAll(): Collection<V> = storage.values

    override fun clear() {
        storage.clear()
        keysQueue.clear()
    }
}