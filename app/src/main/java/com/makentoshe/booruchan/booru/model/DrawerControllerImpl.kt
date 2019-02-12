package com.makentoshe.booruchan.booru.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class DrawerControllerImpl : Controller<DrawerListener>, Serializable,
    DrawerController {

    @Transient
    private val drawerObservable = BehaviorSubject.create<DrawerState>()
    @Transient
    private val disposables = CompositeDisposable()

    override fun subscribe(action: DrawerListener.() -> Unit) {
        val handler = DrawerListener()
        handler.action()
        disposables.add(drawerObservable.subscribe(handler))
    }

    override fun addDrawerListener(init: DrawerListener.() -> Unit) = subscribe(init)

    override val state: DrawerState
        get() = drawerObservable.value ?: DrawerState.DrawerClose

    override fun openDrawer() = newState(DrawerState.DrawerOpen)

    override fun closeDrawer() = newState(DrawerState.DrawerClose)

    private fun newState(state: DrawerState) = drawerObservable.onNext(state)

    override fun clear() = disposables.clear()

}

