package com.makentoshe.booruchan.booru.model

import com.makentoshe.booruchan.Controller
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.Navigator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class ContentScreenControllerImpl : Controller<FragmentScreen> {

    private var localCicerone: Cicerone<Router> = Cicerone.create()

    private val contentScreen = BehaviorSubject.create<FragmentScreen>()
    private val disposables = CompositeDisposable()

    init {
        subscribe { localCicerone.router.replaceScreen(it) }
    }

    override fun subscribe(action: (FragmentScreen) -> Unit) {
        disposables.add(contentScreen.subscribe(action))
    }

    fun newScreen(screen: FragmentScreen) {
        if (contentScreen.hasValue() && contentScreen.value?.javaClass == screen.javaClass) return
        contentScreen.onNext(screen)
    }

    fun update(navigator: Navigator, defaultScreen: FragmentScreen) {
        localCicerone.navigatorHolder.setNavigator(navigator)
        newScreen(contentScreen.value ?: defaultScreen)
    }

    override fun clear() {
        localCicerone.navigatorHolder.removeNavigator()
        disposables.clear()
    }
}