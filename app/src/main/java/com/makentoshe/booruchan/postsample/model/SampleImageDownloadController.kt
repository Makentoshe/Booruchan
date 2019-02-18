package com.makentoshe.booruchan.postsample.model

import com.makentoshe.booruapi.Post
import java.lang.Exception

interface SampleImageDownloadController {
    fun passUrlToTheFile(url: String)
    fun loadSampleImage(post: Post)
    fun onSampleImageLoaded(action: (ByteArray) -> Unit)
    fun onSampleImageLoadingError(action: (Exception) -> Unit)
}

