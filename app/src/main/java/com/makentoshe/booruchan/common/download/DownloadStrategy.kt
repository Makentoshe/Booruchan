package com.makentoshe.booruchan.common.download

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.common.SchedulersProvider
import com.makentoshe.booruchan.repository.Repository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

open class DownloadStrategy(
    private val repository: Repository<Post, ByteArray>,
    private val disposables: CompositeDisposable,
    private val schedulersProvider: SchedulersProvider
) : DownloadListener<ByteArray> {

    private val observable = BehaviorSubject.create<ByteArray>()

    fun start(post: Post) {
        Single.just(repository)
            .subscribeOn(schedulersProvider.background)
            .map { it.get(post) }
            .observeOn(schedulersProvider.foreground)
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