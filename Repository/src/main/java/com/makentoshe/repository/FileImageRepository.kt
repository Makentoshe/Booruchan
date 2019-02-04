package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import java.io.Serializable

/**
 * This repository does not cached the result for future reuse.
 * When image was received it will be return immediately.
 */
class FileImageRepository(private val booru: Booru) : ImageRepository(), Serializable {
    override fun get(key: String) = booru.getPreview(key).readBytes()
}