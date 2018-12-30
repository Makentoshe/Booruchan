package com.makentoshe.booruchan.booru

import com.makentoshe.booruchan.BooruContentScreen
import com.makentoshe.booruchan.Navigator
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class ContentController {

    private var localCicerone: Cicerone<Router> = Cicerone.create()

    private val contentScreen = BehaviorSubject.create<BooruContentScreen>()

    private val contentScreenDisposable = subscribe()

    fun newScreen(screen: BooruContentScreen) = contentScreen.onNext(screen)

    private fun subscribe() = contentScreen.subscribe {
        localCicerone.router.replaceScreen(it)
    }

    fun update(navigator: Navigator) {
        localCicerone.navigatorHolder.setNavigator(navigator)
//        newScreen(contentScreen.value:? TODO)
    }

    fun clear() {
        localCicerone.navigatorHolder.removeNavigator()
    }
}