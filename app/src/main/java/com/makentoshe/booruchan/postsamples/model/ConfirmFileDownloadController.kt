package com.makentoshe.booruchan.postsamples.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class ConfirmFileDownloadController: Controller<String> {
    private val observable = PublishSubject.create<String>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (String) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(string: String) = observable.onNext(string)

    override fun clear() = disposables.clear()
}