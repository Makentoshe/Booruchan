package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.android.arena.PostImageArenaStorage
import java.io.File

class PostPreviewArenaStorage(cacheRootDirectory: File) : PostImageArenaStorage(cacheRootDirectory) {
    override val imageCacheDirectoryTitle = "preview"
}