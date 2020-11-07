package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.core.post.Image

class PostPreviewArenaStorage: ArenaStorage<Image, ByteArray> {

    override fun fetch(key: Image): Result<ByteArray> {

    }

    override fun carry(key: Image, value: ByteArray) {

    }
}