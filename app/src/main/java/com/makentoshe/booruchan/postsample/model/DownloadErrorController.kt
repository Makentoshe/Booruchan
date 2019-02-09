package com.makentoshe.booruchan.postsample.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class DownloadErrorController : Controller<Exception> {

    private val observable = BehaviorSubject.create<Exception>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (Exception) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(exception: Exception) = observable.onNext(exception)

    override fun clear() = disposables.clear()
}