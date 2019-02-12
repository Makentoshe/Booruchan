package com.makentoshe.booruchan.booru.model

import io.reactivex.functions.Consumer

class DrawerListener : Consumer<DrawerState> {

    private var onDrawerClose: (() -> Unit)? = null
    private var onDrawerOpen: (() -> Unit)? = null

    override fun accept(t: DrawerState) {
        when (t) {
            DrawerState.DrawerClose -> onDrawerClose?.invoke()
            DrawerState.DrawerOpen -> onDrawerOpen?.invoke()
        }
    }

    fun onClose(onClose: (() -> Unit)) {
        onDrawerClose = onClose
    }

    fun onOpen(onOpen: (() -> Unit)) {
        onDrawerOpen = onOpen
    }
}