package com.makentoshe.booruchan.repository.cache

import android.content.Context

abstract class InternalCache<K, V>(context: Context, type: InternalCacheType) : ClearableCache<K, V> {

    protected val mainDirectory = context.getDir(type.name, Context.MODE_PRIVATE)!!

    override fun clear() {
        println(mainDirectory.deleteRecursively())
        mainDirectory.mkdirs()
    }
}

