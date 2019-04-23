package com.makentoshe.booruchan.screen.posts.controller

import android.content.Context
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface CacheController {
    fun clearAll()
}

class CacheControllerImpl(
    private val context: Context
) : CacheController {

    override fun clearAll() {
        GlobalScope.launch {
            PostInternalCache(context).clear()
            ImageInternalCache(context, InternalCache.Type.SAMPLE).clear()
            ImageInternalCache(context, InternalCache.Type.PREVIEW).clear()
            ImageInternalCache(context, InternalCache.Type.FILE).clear()
        }
    }
}
