package com.makentoshe.booruchan.booru.model

import com.makentoshe.booruchan.BooruContentScreen
import com.makentoshe.booruchan.Controller
import com.makentoshe.booruchan.Navigator
import com.makentoshe.booruchan.Screen
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class ContentScreenController : Controller<Screen> {

    private var localCicerone: Cicerone<Router> = Cicerone.create()

    private val contentScreen = BehaviorSubject.create<BooruContentScreen>()
    private val disposables = CompositeDisposable()

    init {
        subscribe { localCicerone.router.replaceScreen(it) }
    }

    override fun subscribe(action: (Screen) -> Unit) {
        disposables.add(contentScreen.subscribe(action))
    }

    fun newScreen(screen: BooruContentScreen) {
        if (contentScreen.hasValue() && contentScreen.value?.javaClass == screen.javaClass) return
        contentScreen.onNext(screen)
    }

    fun update(navigator: Navigator, defaultScreen: BooruContentScreen) {
        localCicerone.navigatorHolder.setNavigator(navigator)
        newScreen(contentScreen.value ?: defaultScreen)
    }

    fun clear() {
        localCicerone.navigatorHolder.removeNavigator()
        disposables.clear()
    }
}