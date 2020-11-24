package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.android.arena.PostContentArenaStorage
import java.io.File

class PostPreviewArenaStorage(cacheRootDirectory: File) : PostContentArenaStorage(cacheRootDirectory) {
    override val imageCacheDirectoryTitle = "preview"
}