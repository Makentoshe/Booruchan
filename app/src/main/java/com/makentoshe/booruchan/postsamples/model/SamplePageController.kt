package com.makentoshe.booruchan.postsamples.model

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class SamplePageController : Serializable {

    private val observable = BehaviorSubject.create<Command>()
    private val disposables = CompositeDisposable()

    fun subscribe(action: (Command) -> Unit) = disposables.add(observable.subscribe(action))

    fun block() = observable.onNext(Command.BLOCK)

    fun unblock() = observable.onNext(Command.UNBLOCK)

    fun update() = disposables.clear()

    fun backToPreviews() = observable.onNext(Command.CLOSE)

    enum class Command { BLOCK, UNBLOCK, CLOSE }
}