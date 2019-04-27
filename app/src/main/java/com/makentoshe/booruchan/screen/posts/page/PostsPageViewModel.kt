package com.makentoshe.booruchan.screen.posts.page

import android.util.Log
import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.BuildConfig
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.model.PositionHolder
import com.makentoshe.booruchan.model.TagsHolder
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadEventListener
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class PostsPageViewModel(
    private val booruHolder: BooruHolder,
    private val tagsHolder: TagsHolder,
    private val positionHolder: PositionHolder,
    private val postsDownloadController: PostsDownloadController
) : ViewModel(), BooruHolder, TagsHolder, PositionHolder, PostsDownloadEventListener, KoinComponent {

    override val booru: Booru
        get() = booruHolder.booru

    override val set: MutableSet<Tag>
        get() = tagsHolder.set

    override val position: Int
        get() = positionHolder.position

    /* starts loading on fragment create */
    init {
        val request = get<Posts.Request> { parametersOf(set, position) }
        postsDownloadController.start(request)
    }

    fun init() {
        if (BuildConfig.DEBUG) Log.w(this.javaClass.simpleName, "Init $position")
    }

    /**
     * Calls when posts downloading finished successfully.
     */
    override fun onSuccess(action: (List<Post>) -> Unit) {
        postsDownloadController.onSuccess(action)
    }

    /**
     * Calls when posts downloading finished with an error.
     */
    override fun onError(action: (Throwable) -> Unit) {
        postsDownloadController.onError(action)
    }
}