package com.makentoshe.booruchan.posts

import com.makentoshe.booruchan.Action
import com.makentoshe.booruchan.booru.DrawerController
import com.makentoshe.booruchan.booru.DrawerState
import io.reactivex.subjects.BehaviorSubject

class UIController(
    val overflowController: OverflowController,
    val drawerController: DrawerController
) {

    private val uiActionObservable = BehaviorSubject.create<Action.UIAction>()
    private val uiActionObserver = subscribe()

    private fun subscribe() = uiActionObservable.subscribe {
        when (it) {
            is Action.UIAction.OverflowClick -> overflowClicked()
            is Action.UIAction.MenuClick -> menuClicked()
            is Action.UIAction.ClearTextFieldClick -> clearClicked()
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

    private fun menuClicked() {
        if (drawerController.state == null) {
            drawerController.openDrawer()
            return
        }
        when (drawerController.state) {
            is DrawerState.DrawerOpen -> drawerController.closeDrawer()
            is DrawerState.DrawerClose -> drawerController.openDrawer()
            else -> Unit
        }
    }

    private fun clearClicked() {
        println("Click")
    }

    fun addOverflowListener(init: OverflowController.OverflowListener.() -> Unit) {
        overflowController.addOverflowListener(init)
    }
}