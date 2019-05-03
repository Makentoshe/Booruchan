package com.makentoshe.booruchan.screen.sampleinfo

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadEventListener
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SampleInfoViewModel(
    private val booru: Booru, tags: Set<Tag>, position: Int, private val disposables: CompositeDisposable
) : ViewModel(),
    PostsDownloadEventListener,
    KoinComponent {

    private val repositoryFactory by inject<StreamRepositoryFactory> {
        parametersOf(booru, null)
    }

    private val downloadController =
        PostsDownloadController
            .build(repositoryFactory, disposables)

    init {
        downloadController.start(Posts.Request(1, tags, position))
    }

    override fun onSuccess(action: (List<Post>) -> Unit) {
        downloadController.onSuccess(action)
    }

    override fun onError(action: (Throwable) -> Unit) {
        downloadController.onError(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}