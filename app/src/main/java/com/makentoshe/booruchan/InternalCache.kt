package com.makentoshe.booruchan

import android.content.Context
import com.makentoshe.repository.cache.ClearableCache

abstract class InternalCache<K, V>(context: Context, title: String) : ClearableCache<K, V> {

    protected val mainDirectory = context.getDir(title, Context.MODE_PRIVATE)!!

    override fun clear() {
        val iterator = mainDirectory.listFiles().asIterable().iterator()
        while (iterator.hasNext()) {
            iterator.next().delete()
        }
    }
}