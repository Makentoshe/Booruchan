package com.makentoshe.booruchan.screen.posts

import android.util.Log
import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.BuildConfig
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.screen.posts.controller.TagsHolder
import io.reactivex.disposables.Disposable

class PostsPageViewModel(
    private val booruHolder: BooruHolder,
    private val tagsHolder: TagsHolder,
    private val position: Int,
    private val postsDownloadController: PostsDownloadController,
    private val disposables: Disposable
) : ViewModel(), BooruHolder,
    TagsHolder,
    PostsDownloadEventListener{

    override val booru: Booru
        get() = booruHolder.booru

    override val set: MutableSet<Tag>
        get() = tagsHolder.set

    init {
        postsDownloadController.start()
    }

    fun init() {
        if (BuildConfig.DEBUG) Log.w(this.javaClass.simpleName, "Init $position")
    }

    override fun onSuccess(action: (List<Post>) -> Unit) {
        postsDownloadController.onSuccess(action)
    }

    override fun onError(action: (Throwable) -> Unit) {
        postsDownloadController.onError(action)
    }

    override fun onCleared() = disposables.dispose()
}