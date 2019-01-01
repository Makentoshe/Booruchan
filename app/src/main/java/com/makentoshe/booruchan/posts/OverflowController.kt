package com.makentoshe.booruchan.posts

import android.annotation.SuppressLint
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

class OverflowController {

    private val overflowStateObservable = BehaviorSubject.create<OverflowState>()
    private val overflowStateObservers = HashMap<Any, Disposable>()

    val value: OverflowState?
        get() = overflowStateObservable.value

    fun newState(state: OverflowState) = overflowStateObservable.onNext(state)

    @SuppressLint("CheckResult")
    fun addOverflowListener(init: OverflowListener.() -> Unit) {
        val handler = OverflowListener()
        handler.init()
        overflowStateObservable.subscribe(handler)
    }

    class OverflowListener : Consumer<OverflowState> {

        private var onMagnifyListener: ((OverflowState.Magnify) -> Unit)? = null
        private var onCrossListener: ((OverflowState.Cross) -> Unit)? = null
        private var onTransitionListener: ((OverflowState.Transition) -> Unit)? = null

        override fun accept(t: OverflowState) {
            when (t) {
                OverflowState.Magnify -> onMagnifyListener?.invoke(OverflowState.Magnify)
                OverflowState.Cross -> onCrossListener?.invoke(OverflowState.Cross)
                is OverflowState.Transition -> onTransitionListener?.invoke(t)
            }
        }

        fun onMagnify(onMagnify: ((OverflowState.Magnify) -> Unit)) {
            onMagnifyListener = onMagnify
        }

        fun onCross(onCross: ((OverflowState.Cross) -> Unit)) {
            onCrossListener = onCross
        }

        fun onTransition(onTransition: ((OverflowState.Transition) -> Unit)) {
            onTransitionListener = onTransition
        }

    }
}