package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * Class performs image downloading from repository.
 */
class ImageDownloadStrategy(
    private val repository: Repository<Post, ByteArray>,
    private val disposables: CompositeDisposable
): ImageDownloadListener {

    private val observable = BehaviorSubject.create<Bitmap>()

    private val errorListenerList = LinkedList<(Throwable) -> Unit>()

    fun start(post: Post) {
        Single.just(repository)
            .subscribeOn(Schedulers.io())
            .map { it.get(post) }
            .map { BitmapFactory.decodeByteArray(it, 0, it.size) }
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable().safeSubscribe(observable)
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