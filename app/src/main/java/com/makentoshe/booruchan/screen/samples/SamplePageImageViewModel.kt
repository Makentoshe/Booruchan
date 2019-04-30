package com.makentoshe.booruchan.screen.samples

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import com.makentoshe.booruchan.screen.samples.model.FileImageDownloadStrategy
import com.makentoshe.booruchan.screen.samples.model.SampleImageDownloadStrategy
import io.reactivex.disposables.CompositeDisposable

class SamplePageImageViewModel(
    booru: Booru, post: Post,
    disposables: CompositeDisposable,
    streamListener: StreamDownloadController
) : ViewModel(),
    ImageDownloadListener {

    private val sampleDownloadStrategy =
        SampleImageDownloadStrategy(booru, streamListener, disposables)

    private val fileDownloadStrategy =
        FileImageDownloadStrategy(booru, streamListener, disposables)

    init {
        sampleDownloadStrategy.start(post)

        sampleDownloadStrategy.onError {
            fileDownloadStrategy.start(post)
        }
    }

    override fun onSuccess(action: (Bitmap) -> Unit) {
        sampleDownloadStrategy.onSuccess(action)
        fileDownloadStrategy.onSuccess(action)
    }

    override fun onError(action: (Throwable) -> Unit) {
        fileDownloadStrategy.onError(action)
    }
}