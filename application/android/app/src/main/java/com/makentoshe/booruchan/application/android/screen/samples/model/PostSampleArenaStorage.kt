package com.makentoshe.booruchan.application.android.screen.samples.model

import com.makentoshe.booruchan.application.android.arena.ContentArenaStorage
import java.io.File

class PostSampleArenaStorage(cacheRootDirectory: File) : ContentArenaStorage(cacheRootDirectory) {
    override val imageCacheDirectoryTitle = "sample"
}
