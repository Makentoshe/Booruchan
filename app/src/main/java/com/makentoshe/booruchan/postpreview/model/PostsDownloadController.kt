package com.makentoshe.booruchan.postpreview.model

import com.makentoshe.booruapi.Posts
import com.makentoshe.controllers.DownloadResult

interface PostsDownloadController {

    val position: Int

    fun loadPosts(page: Int)

    fun addOnPostsReceiveListener(action: (DownloadResult<Posts>) -> Unit)
}