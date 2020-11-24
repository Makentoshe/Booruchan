package com.makentoshe.booruchan.application.android.screen.samples.model

import com.makentoshe.booruchan.application.android.arena.PostImageArenaStorage
import java.io.File

class PostSampleArenaStorage(cacheRootDirectory: File) : PostImageArenaStorage(cacheRootDirectory) {
    override val imageCacheDirectoryTitle = "sample"
}
