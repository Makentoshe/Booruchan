package com.makentoshe.booruchan.screen.samples

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.common.download.DownloadStrategy
import com.makentoshe.booruchan.common.download.GifDownloadListener
import com.makentoshe.booruchan.common.download.GifDownloadStrategy
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import pl.droidsonroids.gif.GifDrawable

class SamplePageGifViewModel(
    booru: Booru, post: Post,
    private val disposables: CompositeDisposable,
    streamController: StreamDownloadController
) : ViewModel(), KoinComponent, GifDownloadListener {

    private val repositoryFactory = get<StreamRepositoryFactory> { parametersOf(booru, streamController) }

    private val sampleDownloadStrategy = initSampleDownloadStrategy(disposables)

    private fun initSampleDownloadStrategy(disposables: CompositeDisposable): GifDownloadStrategy {
        val repository = repositoryFactory.buildSamplesRepository()
        return GifDownloadStrategy(
            DownloadStrategy(
                repository,
                disposables,
                get()))
    }

    private val fileDownloadStrategy = initFileDownloadStrategy(disposables)

    private fun initFileDownloadStrategy(disposables: CompositeDisposable): GifDownloadStrategy {
        val repository = repositoryFactory.buildFilesRepository()
        return GifDownloadStrategy(
            DownloadStrategy(
                repository,
                disposables,
                get()))
    }

    init {
        //starts getting content from sample repository
        sampleDownloadStrategy.start(post)
        //if content was got unsuccessfully
        sampleDownloadStrategy.onError {
            //tries with file repository
            fileDownloadStrategy.start(post)
        }
    }

    override fun onSuccess(action: (GifDrawable) -> Unit) {
        sampleDownloadStrategy.onSuccess(action)
        fileDownloadStrategy.onSuccess(action)
    }

    override fun onError(action: (Throwable) -> Unit) {
        fileDownloadStrategy.onError(action)
    }

    override fun onCleared() {
        disposables.clear()
    }
}