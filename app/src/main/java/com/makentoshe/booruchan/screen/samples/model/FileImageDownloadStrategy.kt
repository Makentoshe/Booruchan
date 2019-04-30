package com.makentoshe.booruchan.screen.samples.model

import android.graphics.Bitmap
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadStrategy
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class FileImageDownloadStrategy(
    booru: Booru, streamListener: StreamDownloadController, disposables: CompositeDisposable
) : ImageDownloadListener,
    KoinComponent {

    private val repository by lazy {
        val factory = get<StreamRepositoryFactory> {
            parametersOf(
                booru,
                streamListener
            )
        }
        factory.buildFilesRepository()
    }

    private val imageDownloadStrategy =
        ImageDownloadStrategy(
            repository,
            disposables
        )

    fun start(post: Post) = imageDownloadStrategy.start(post)

    override fun onSuccess(action: (Bitmap) -> Unit) {
        imageDownloadStrategy.onSuccess(action)
    }

    override fun onError(action: (Throwable) -> Unit) {
        imageDownloadStrategy.onError(action)
    }
}