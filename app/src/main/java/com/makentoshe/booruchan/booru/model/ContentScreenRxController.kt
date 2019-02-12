package com.makentoshe.booruchan.booru.model

import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.Navigator
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class ContentScreenRxController : SimpleRxController<FragmentScreen, FragmentScreen>(BehaviorSubject.create()) {

    private var localCicerone: Cicerone<Router> = Cicerone.create()

    private val contentScreen = BehaviorSubject.create<FragmentScreen>()
    private val disposables = CompositeDisposable()

    init {
        subscribe { localCicerone.router.replaceScreen(it) }
    }

    override fun subscribe(action: (FragmentScreen) -> Unit) : Disposable {
        return contentScreen.subscribe(action).also{ disposables.add(it) }
    }

    override fun action(screen: FragmentScreen) {
        if (contentScreen.hasValue() && contentScreen.value?.javaClass == screen.javaClass) return
        contentScreen.onNext(screen)
    }

    fun update(navigator: Navigator, defaultScreen: FragmentScreen) {
        localCicerone.navigatorHolder.setNavigator(navigator)
        this.action(contentScreen.value ?: defaultScreen)
    }

    override fun clear() {
        localCicerone.navigatorHolder.removeNavigator()
        disposables.clear()
    }
}