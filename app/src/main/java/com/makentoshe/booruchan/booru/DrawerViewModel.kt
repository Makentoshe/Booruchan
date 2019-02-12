package com.makentoshe.booruchan.booru

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.booru.model.DrawerRxController
import com.makentoshe.booruchan.booru.model.DrawerListener
import com.makentoshe.booruchan.booru.model.DrawerState
import com.makentoshe.viewmodel.ViewModel

/** Class wraps [DrawerRxController]. */
class DrawerViewModel(
    private val drawerRxController: DrawerRxController
) : ViewModel(), DrawerController {

    /** Add [DrawerListener] for [DrawerRxController] events. */
    override fun addDrawerListener(init: DrawerListener.() -> Unit) {
        drawerRxController.subscribe(init)
    }

    /** Closed the drawer if it is open. */
    override fun closeDrawer() = drawerRxController.closeDrawer()

    /** Opens the drawer if it is close. */
    override fun openDrawer() = drawerRxController.openDrawer()

    /** Returns a current drawer state. Can be [DrawerState.DrawerClose] or [DrawerState.DrawerOpen]. */
    override val state: DrawerState
        get() = drawerRxController.state

    /* Calls when the onCreateView called in the owner */
    override fun onCreateView(owner: Fragment) = drawerRxController.clear()

    /* Calls when the current ViewModel instance is no longer required */
    override fun onCleared() {
        super.onCleared()
        drawerRxController.clear()
    }

    /** Factory class for creating a [DrawerViewModel] instance. */
    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            return DrawerViewModel(DrawerRxController()) as T
        }
    }
}