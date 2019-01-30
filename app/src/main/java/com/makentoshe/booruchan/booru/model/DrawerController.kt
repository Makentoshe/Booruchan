package com.makentoshe.booruchan.booru.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class DrawerController : Controller<DrawerController.DrawerListener>, Serializable {

    @Transient
    private val drawerObservable = BehaviorSubject.create<DrawerState>()
    @Transient
    private val disposables = CompositeDisposable()

    override fun subscribe(action: DrawerListener.() -> Unit) {
        val handler = DrawerListener()
        handler.action()
        disposables.add(drawerObservable.subscribe(handler))
    }

    val state: DrawerState?
        get() = drawerObservable.value

    fun openDrawer() = newState(DrawerState.DrawerOpen)

    fun closeDrawer() = newState(DrawerState.DrawerClose)

    private fun newState(state: DrawerState) = drawerObservable.onNext(state)

    fun clear() = disposables.clear()

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