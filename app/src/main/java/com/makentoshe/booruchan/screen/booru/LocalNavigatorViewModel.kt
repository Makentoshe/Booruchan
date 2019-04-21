package com.makentoshe.booruchan.screen.booru

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.model.CiceroneFactory
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.booru.model.LocalNavigator
import ru.terrakok.cicerone.Cicerone

class LocalNavigatorViewModel(
    private val cicerone: Cicerone<Router>,
    private val localRouter: LocalRouter
) : ViewModel(), LocalNavigator {

    private var isScreenSetuped = false

    override fun setNavigator(navigator: FragmentNavigator) {
        cicerone.navigatorHolder.setNavigator(navigator)
        if (!isScreenSetuped) navigateToPosts()
    }

    override fun removeNavigator() {
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun navigateToPosts() {
        localRouter.navigateToPosts(cicerone.router)
        isScreenSetuped = true
    }

    override fun navigateToAccount() {
        localRouter.navigateToAccount(cicerone.router)
        isScreenSetuped = true
    }
}