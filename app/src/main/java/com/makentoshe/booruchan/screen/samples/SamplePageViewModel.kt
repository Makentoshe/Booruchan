package com.makentoshe.booruchan.screen.samples

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.model.PositionHolder
import com.makentoshe.booruchan.model.TagsHolder
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadEventListener

class SamplePageViewModel(
    override val booru: Booru,
    override val tags: MutableSet<Tag>,
    override val position: Int,
    private val postsDownloadController: PostsDownloadController
) : ViewModel(), BooruHolder, TagsHolder, PositionHolder, PostsDownloadEventListener {

    init {
        val request = Posts.Request(1, tags, position)
        postsDownloadController.start(request)
    }

    override fun onSuccess(action: (List<Post>) -> Unit) {
        postsDownloadController.onSuccess(action)
    }

    override fun onError(action: (Throwable) -> Unit) {
        postsDownloadController.onError(action)
    }

    fun init() = Unit
}