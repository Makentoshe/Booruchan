package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.repository.cache.Cache
import java.io.InputStream
import java.lang.Exception

class InputStreamPreviewRepository(
    private val booru: Booru,
    private val cache: Cache<String, InputStream>
) : InputStreamRepository<String>() {

    override fun get(key: String): InputStream? {
        return try {
            cache.get(key) { booru.getPreview(key) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}