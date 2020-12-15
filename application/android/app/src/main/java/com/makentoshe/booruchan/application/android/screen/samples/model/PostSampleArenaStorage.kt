package com.makentoshe.booruchan.application.android.screen.samples.model

import com.makentoshe.booruchan.application.android.arena.PostContentArenaStorage
import java.io.File

class PostSampleArenaStorage(cacheRootDirectory: File) : PostContentArenaStorage(cacheRootDirectory) {
    override val imageCacheDirectoryTitle = "sample"
}
