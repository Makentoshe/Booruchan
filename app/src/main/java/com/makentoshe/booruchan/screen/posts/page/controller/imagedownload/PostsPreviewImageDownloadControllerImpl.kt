package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import android.graphics.Bitmap
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.factory.RepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.PreviewImageDownloadStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Controller performs previews image downloading.
 *
 * @param repositoryFactory is a source.
 * @param disposables is a disposables container for releasing in future.
 */
class PostsPreviewImageDownloadControllerImpl(
    private val repositoryFactory: RepositoryFactory,
    private val disposables: CompositeDisposable
) : PostsPreviewImageDownloadController {

    private val observable = BehaviorSubject.create<Bitmap>()

    override fun start(post: Post) {
        val previewRepository = repositoryFactory.buildPreviewsRepository()
        val previewStrategy = PreviewImageDownloadStrategy(previewRepository, disposables)
        //start preview downloading
        previewStrategy.start(post)

        previewStrategy.onSuccess { observable.onNext(it) }
        //replace on error by new strategy (sample)
        previewStrategy.onError {
            //call here new strategy
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
