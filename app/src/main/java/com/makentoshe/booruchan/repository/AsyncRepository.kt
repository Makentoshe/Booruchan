package com.makentoshe.booruchan.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class AsyncRepository<K, V>(
    private val coroutineContext: CoroutineContext,
    private val repository: Repository<K, V>
) : Repository<K, V> {

    override fun get(key: K) = repository.get(key)

    open suspend fun asyncget(key: K): V? {
        return GlobalScope.async(coroutineContext) { get(key) }.await()
    }

    open fun get(key: K, action: (V?) -> Unit) {
        GlobalScope.launch(coroutineContext) {
            action(asyncget(key))
        }
    }
}