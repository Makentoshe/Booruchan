package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import android.graphics.Bitmap
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.common.download.DownloadStrategy
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.get

/**
 * Controller start preview image downloading.
 */
interface PreviewImageDownloadController : ImageDownloadListener {

    /**
     * Start preview image downloading using a post.
     */
    fun start(post: Post)

    companion object {
        fun build(
            repositoryFactory: StreamRepositoryFactory,
            disposables: CompositeDisposable
        ): PreviewImageDownloadController {
            return PreviewImageDownloadControllerImpl(repositoryFactory, disposables)
        }
    }

    /**
     * Controller performs previews image downloading. It uses some downloading strategies.
     * If a preview image was not downloaded or correctly decoded from a byte array into bitmap,
     * then it's tries to make a same algorithm but with another repository (sample or file).
     *
     * @param repositoryFactory is a source.
     * @param disposables is a disposables container for releasing in future.
     */
    private class PreviewImageDownloadControllerImpl(
        private val repositoryFactory: StreamRepositoryFactory,
        private val disposables: CompositeDisposable
    ) : PreviewImageDownloadController, KoinComponent {

        private val observable = BehaviorSubject.create<Bitmap>()

        override fun start(post: Post) {
            val previewRepository = repositoryFactory.buildPreviewsRepository()
            val downloadStrategy =
                DownloadStrategy(previewRepository, disposables, get())
            val previewStrategy = ImageDownloadStrategy(downloadStrategy)
            //start preview downloading
            previewStrategy.start(post)

            previewStrategy.onSuccess { observable.onNext(it) }
            //tries a new strategy - sample
            previewStrategy.onError { alternativeStrategySample(post) }
        }

        private fun alternativeStrategySample(post: Post) {
            val sampleRepository = repositoryFactory.buildSamplesRepository()
            val downloadStrategy =
                DownloadStrategy(sampleRepository, disposables, get())
            val sampleStrategy = ImageDownloadStrategy(downloadStrategy)
            //start sample downloading
            sampleStrategy.start(post)

            //send downloaded sample as a preview image
            sampleStrategy.onSuccess { observable.onNext(it) }
            sampleStrategy.onError {
                //call here a new strategy
                observable.onError(it)
            }
        }

        override fun onSuccess(action: (Bitmap) -> Unit) {
            onComplete { p, _ -> if (p != null) action(p) }
        }

        override fun onError(action: (Throwable) -> Unit) {
            onComplete { _, t -> if (t != null) action(t) }
        }

        private fun onComplete(action: (Bitmap?, Throwable?) -> Unit) {
            val disposable = observable.subscribe({ action(it, null) }, { action(null, it) })
            disposables.add(disposable)
        }
    }
}

