package com.makentoshe.booruchan.repository.cache

import android.content.Context
import com.makentoshe.booruchan.repository.cache.ClearableCache
import java.nio.file.Files
import java.nio.file.Path

abstract class InternalCache<K, V>(context: Context, title: String) : ClearableCache<K, V> {

    protected val mainDirectory = context.getDir(title, Context.MODE_PRIVATE)!!

    override fun clear() {
        println(mainDirectory.deleteRecursively())
        mainDirectory.mkdirs()
    }
}