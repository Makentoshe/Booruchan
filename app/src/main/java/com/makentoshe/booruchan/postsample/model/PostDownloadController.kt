package com.makentoshe.booruchan.postsample.model

import com.makentoshe.booruapi.Posts
import com.makentoshe.controllers.DownloadResult

interface PostDownloadController {
    fun loadPost(position: Int)
    fun onPostLoadedListener(action: (DownloadResult<Posts>) -> Unit)
}