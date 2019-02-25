package com.makentoshe.repository

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import java.io.Serializable

class FileRepository(private val booru: Booru) : Repository<Post, ByteArray>, Serializable {
    override fun get(key: Post) = booru.getPreview(key.fileUrl).readBytes()
}