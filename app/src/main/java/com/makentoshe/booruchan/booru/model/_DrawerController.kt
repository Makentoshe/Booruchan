package com.makentoshe.booruchan.booru.model

import android.annotation.SuppressLint
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class _DrawerController : Serializable {
    @Transient
    private lateinit var drawerObservable: BehaviorSubject<DrawerState>

    val value: DrawerState?
        get() = drawerObservable.value

    fun newState(state: DrawerState) = drawerObservable.onNext(state)

    @SuppressLint("CheckResult")
    fun addDrawerListener(init: DrawerListener.() -> Unit) {
        val handler = DrawerListener()
        handler.init()
        drawerObservable.subscribe(handler)
    }

    fun update() {
        drawerObservable = BehaviorSubject.create<DrawerState>()
    }

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
}

