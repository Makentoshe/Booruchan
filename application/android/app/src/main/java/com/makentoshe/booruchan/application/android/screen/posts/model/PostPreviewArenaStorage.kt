package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.core.post.Image
import java.io.File

class PostPreviewArenaStorage(
    private val cacheRootDirectory: File
): ArenaStorage<Image, ByteArray> {

    override fun fetch(key: Image): Result<ByteArray> {
        return Result.failure(Exception("TODO"))
    }

    override fun carry(key: Image, value: ByteArray) {

    }
}