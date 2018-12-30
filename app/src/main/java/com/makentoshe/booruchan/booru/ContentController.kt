package com.makentoshe.booruchan.booru

import com.makentoshe.booruchan.BooruContentScreen
import com.makentoshe.booruchan.Navigator
import com.makentoshe.booruchan.PostsScreen
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class ContentController {

    private var localCicerone: Cicerone<Router> = Cicerone.create()

    private val contentScreen = BehaviorSubject.create<BooruContentScreen>()

    private val contentScreenDisposable = subscribe()

    fun newScreen(screen: BooruContentScreen) {
        if (contentScreen.hasValue() && contentScreen.value?.javaClass == screen.javaClass) return
        contentScreen.onNext(screen)
    }

    private fun subscribe() = contentScreen.subscribe {
        localCicerone.router.replaceScreen(it)
    }

    fun update(navigator: Navigator, defaultScreen: BooruContentScreen) {
        localCicerone.navigatorHolder.setNavigator(navigator)
        newScreen(contentScreen.value ?: defaultScreen)
    }

    fun clear() {
        localCicerone.navigatorHolder.removeNavigator()
    }
}