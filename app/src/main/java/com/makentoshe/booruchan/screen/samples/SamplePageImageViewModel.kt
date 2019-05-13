package com.makentoshe.booruchan.screen.samples

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.common.download.DownloadStrategy
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadStrategy
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class SamplePageImageViewModel(
    booru: Booru, post: Post, disposables: CompositeDisposable, streamListener: StreamDownloadController
) : ViewModel(), ImageDownloadListener, KoinComponent {

    private val repositoryFactory = get<StreamRepositoryFactory> { parametersOf(booru, streamListener) }

    private val sampleDownloadStrategy = initSampleDownloadStrategy(disposables)

    private fun initSampleDownloadStrategy(disposables: CompositeDisposable): ImageDownloadStrategy {
        val repository = repositoryFactory.buildSamplesRepository()
        return ImageDownloadStrategy(
            DownloadStrategy(
                repository,
                disposables,
                get()))
    }

    private val fileDownloadStrategy = initFileDownloadStrategy(disposables)

    private fun initFileDownloadStrategy(disposables: CompositeDisposable): ImageDownloadStrategy {
        val repository = repositoryFactory.buildFilesRepository()
        return ImageDownloadStrategy(
            DownloadStrategy(
                repository,
                disposables,
                get()))
    }

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