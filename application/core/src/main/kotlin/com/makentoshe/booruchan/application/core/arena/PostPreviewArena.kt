package com.makentoshe.booruchan.application.core.arena

import com.makentoshe.booruchan.core.post.Image

class PostPreviewArena(
    postPreviewArena: ArenaStorage<Image, ByteArray>
) : StorageFirstArena<Image, ByteArray>(postPreviewArena) {

    override suspend fun internalSuspendFetch(key: Image): Result<ByteArray> {

    }
}