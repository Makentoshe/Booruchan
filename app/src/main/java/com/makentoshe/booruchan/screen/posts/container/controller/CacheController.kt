package com.makentoshe.booruchan.screen.posts.container.controller

import com.makentoshe.booruchan.repository.cache.Cache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface CacheController {

    fun clearAll()

    companion object {
        fun create(vararg cache: Cache<*, *>, context: CoroutineContext = Dispatchers.Default) =
            object : CacheController {
                override fun clearAll() {
                    GlobalScope.launch(context) { cache.forEach { it.clear() } }
                }
            }
    }
}
