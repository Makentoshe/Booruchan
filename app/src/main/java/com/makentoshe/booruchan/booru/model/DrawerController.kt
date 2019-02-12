package com.makentoshe.booruchan.booru.model

import java.io.Serializable

/**
 * This object controls the [androidx.drawerlayout.widget.DrawerLayout].
 */
interface DrawerController : Serializable {
    /** Closes the drawer */
    fun closeDrawer()

    /** Opens the drawer */
    fun openDrawer()

    /** Add a drawer listener */
    fun addDrawerListener(init: DrawerListener.() -> Unit)

    /** Returns a current [DrawerState]. Can be [DrawerState.DrawerOpen] or [DrawerState.DrawerClose]*/
    val state: DrawerState
}