package com.makentoshe.controllers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject

abstract class SimpleRxController<P, A>(protected var observable: Subject<P>) :
    RxController<P, A> {
    @Transient
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (P) -> Unit): Disposable {
        val disposable = observable.subscribe(action)
        disposables.add(disposable)
        return disposable
    }

    override fun clear() = disposables.clear()
}

