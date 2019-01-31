package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class ClearIconController: Controller<Unit> {

    private val clearIconObservable = BehaviorSubject.create<Unit>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (Unit) -> Unit) {
        disposables.add(clearIconObservable.subscribe(action))
    }

    fun click() = clearIconObservable.onNext(Unit)

    fun clear() = disposables.clear()
}