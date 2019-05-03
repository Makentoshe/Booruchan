package com.makentoshe.booruchan.repository.cache

import android.content.Context

/**
 * Cache used android's internal storage for containing values V by key K
 */
abstract class InternalCache<K, V>(context: Context, type: Type) : Cache<K, V> {

    protected val mainDirectory = context.getDir(type.name, Context.MODE_PRIVATE)!!

    override fun clear() {
        mainDirectory.deleteRecursively()
        mainDirectory.mkdirs()
    }

    enum class Type {
        PREVIEW, POST, SAMPLE, FILE
    }
}

