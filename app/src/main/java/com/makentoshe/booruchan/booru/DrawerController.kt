package com.makentoshe.booruchan.booru

import java.io.Serializable

class DrawerController(private val drawerController: _DrawerController): Serializable {

    val state: DrawerState?
        get() = drawerController.value

    fun openDrawer() = drawerController.newState(DrawerState.DrawerOpen)

    fun closeDrawer() = drawerController.newState(DrawerState.DrawerClose)
}