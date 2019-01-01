package com.makentoshe.booruchan.booru

sealed class DrawerState {
    object DrawerOpen : DrawerState()
    object DrawerClose : DrawerState()
}