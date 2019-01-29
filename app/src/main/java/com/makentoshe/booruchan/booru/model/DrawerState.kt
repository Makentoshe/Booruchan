package com.makentoshe.booruchan.booru.model

sealed class DrawerState {
    object DrawerOpen : DrawerState()
    object DrawerClose : DrawerState()
}