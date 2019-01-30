package com.makentoshe.booruchan.posts.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverflowController(
    private val coroutineScope: CoroutineScope
) : Controller<OverflowController.OverflowListener> {

    private val observable = BehaviorSubject.create<OverflowState>()
    private val disposables = CompositeDisposable()

    val state: OverflowState?
        get() = observable.value

    fun newState(finishState: OverflowState) = observable.onNext(OverflowState.Transition(finishState))

    override fun subscribe(action: OverflowListener.() -> Unit) {
        val handler = OverflowListener()
        handler.action()
        disposables.add(observable.subscribe(handler))
    }

    fun update() {
        clear()
        subscribe {
            onTransition {
                coroutineScope.launch {
                    delay(it.transitionDuration)
                    observable.onNext(it.finishState)
                }
            }
        }
    }

    fun clear() = disposables.clear()

    sealed class OverflowState {
        @JvmField
        val transitionDuration = 200L

        object Magnify : OverflowState()
        object Cross : OverflowState()
        class Transition(val finishState: OverflowState) : OverflowState()
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