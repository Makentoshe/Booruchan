package com.makentoshe.booruchan.booru

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.booru.model.DrawerControllerImpl
import com.makentoshe.booruchan.booru.model.DrawerListener
import com.makentoshe.booruchan.booru.model.DrawerState
import com.makentoshe.viewmodel.ViewModel

/** Class wraps [DrawerControllerImpl]. */
class DrawerViewModel(
    private val drawerController: DrawerControllerImpl
) : ViewModel(), DrawerController {

    /** Add [DrawerListener] for [DrawerControllerImpl] events. */
    override fun addDrawerListener(init: DrawerListener.() -> Unit) {
        drawerController.subscribe(init)
    }

    /** Closed the drawer if it is open. */
    override fun closeDrawer() = drawerController.closeDrawer()

    /** Opens the drawer if it is close. */
    override fun openDrawer() = drawerController.openDrawer()

    /** Returns a current drawer state. Can be [DrawerState.DrawerClose] or [DrawerState.DrawerOpen]. */
    override val state: DrawerState
        get() = drawerController.state

    /* Calls when the onCreateView called in the owner */
    override fun onCreateView(owner: Fragment) = drawerController.clear()

    /* Calls when the current ViewModel instance is no longer required */
    override fun onCleared() {
        super.onCleared()
        drawerController.clear()
    }

    /** Factory class for creating a [DrawerViewModel] instance. */
    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            return DrawerViewModel(DrawerControllerImpl()) as T
        }
    }
}