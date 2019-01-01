package com.makentoshe.booruchan.posts

import com.makentoshe.booruchan.Action
import io.reactivex.subjects.BehaviorSubject

class UIController(
    val overflowController: OverflowController) {

    private val uiActionObservable = BehaviorSubject.create<Action.UIAction>()
    private val uiActionObserver = subscribe()

    private fun subscribe() = uiActionObservable.subscribe {
        when (it) {
            is Action.UIAction.OverflowClick -> overflowClicked()
        }
    }

    fun action(action: Action.UIAction) = uiActionObservable.onNext(action)

    private fun overflowClicked() {
        if (overflowController.value == null) {
            overflowController.newState(OverflowState.Transition(OverflowState.Cross))
            return
        }
        when (overflowController.value) {
            is OverflowState.Magnify ->
                overflowController.newState(OverflowState.Transition(OverflowState.Cross))
            is OverflowState.Cross ->
                overflowController.newState(OverflowState.Transition(OverflowState.Magnify))
            else -> Unit
        }
    }

    fun addOverflowListener(init: OverflowController.OverflowListener.() -> Unit)  {
        overflowController.addOverflowListener(init)
    }
}