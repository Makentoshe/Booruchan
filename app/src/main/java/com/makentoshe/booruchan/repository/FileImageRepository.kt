package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post

class FileImageRepository(private val booru: Booru) : Repository<Post, ByteArray> {
    override fun get(key: Post) = booru.getCustom().request(key.fileUrl).stream.readBytes()
}