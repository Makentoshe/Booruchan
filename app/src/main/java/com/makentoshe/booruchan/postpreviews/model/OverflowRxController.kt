package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.controllers.RxController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OverflowRxController(
    private val coroutineScope: CoroutineScope
) : RxController<OverflowRxController.OverflowListener, OverflowRxController.OverflowState> {

    private val observable = BehaviorSubject.create<OverflowState>()
    private val disposables = CompositeDisposable()

    val state: OverflowState?
        get() = observable.value

    /**
     * @param param is a finish state.
     */
    override fun action(param: OverflowState) = observable.onNext(OverflowState.Transition(param))

    override fun subscribe(action: OverflowListener.() -> Unit): Disposable {
        val handler = OverflowListener()
        handler.action()
        val disposable = observable.subscribe(handler)
        disposables.add(disposable)
        return disposable
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

    override fun clear() = disposables.clear()

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