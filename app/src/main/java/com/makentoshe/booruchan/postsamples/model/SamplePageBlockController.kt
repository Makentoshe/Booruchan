package com.makentoshe.booruchan.postsamples.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class SamplePageBlockController: Controller<SamplePageBlockController.Command>, Serializable {
    enum class Command { BLOCK, UNBLOCK, CLOSE }

    @Transient
    private val observable = BehaviorSubject.create<Command>()
    @Transient
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (Command) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun newState(command: Command) = observable.onNext(command)

    fun clear() = disposables.clear()
}