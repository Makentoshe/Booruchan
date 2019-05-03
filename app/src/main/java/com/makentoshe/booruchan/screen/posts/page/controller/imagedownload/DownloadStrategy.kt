package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

open class DownloadStrategy(
    private val repository: Repository<Post, ByteArray>,
    private val disposables: CompositeDisposable
) : DownloadListener<ByteArray> {

    private val observable = BehaviorSubject.create<ByteArray>()

    fun start(post: Post) {
        Single.just(repository)
            .subscribeOn(Schedulers.io())
            .map { it.get(post) }
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable().safeSubscribe(observable)
    }

    override fun onSuccess(action: (ByteArray) -> Unit) {
        onComplete { p, _ -> if (p != null) action(p) }
    }

    override fun onError(action: (Throwable) -> Unit) {
        onComplete { _, t -> if (t != null) action(t) }
    }

    private fun onComplete(action: (ByteArray?, Throwable?) -> Unit) {
        val disposable = observable.subscribe({ action(it, null) }, { action(null, it) })
        disposables.add(disposable)
    }
}