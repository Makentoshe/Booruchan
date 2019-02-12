package com.makentoshe.booruchan.booru.model

import com.makentoshe.controllers.RxController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class DrawerRxController : RxController<DrawerListener, DrawerState>, Serializable {
    private val observable = BehaviorSubject.create<DrawerState>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: DrawerListener.() -> Unit): Disposable {
        val handler = DrawerListener()
        handler.action()
        val disposable = observable.subscribe(handler)
        disposables.add(disposable)
        return disposable
    }

    val state: DrawerState
        get() = observable.value ?: DrawerState.DrawerClose

    fun openDrawer() = action(DrawerState.DrawerOpen)

    fun closeDrawer() = action(DrawerState.DrawerClose)

    override fun action(param: DrawerState) = observable.onNext(param)

    override fun clear() = disposables.clear()

}

