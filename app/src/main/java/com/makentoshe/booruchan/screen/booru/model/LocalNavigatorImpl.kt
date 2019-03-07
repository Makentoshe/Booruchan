package com.makentoshe.booruchan.screen.booru.model

import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.account.AccountScreen
import com.makentoshe.booruchan.screen.posts.PostsScreen
import ru.terrakok.cicerone.Cicerone

class LocalNavigatorImpl : LocalNavigator {

    private val cicerone = Cicerone.create(Router())

    override fun setNavigator(navigator: FragmentNavigator) {
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun removeNavigator() {
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun navigateToPosts() {
        val screen = PostsScreen()
        cicerone.router.replaceScreen(screen)
    }

    override fun navigateToAccount() {
        val screen = AccountScreen()
        cicerone.router.replaceScreen(screen)
    }
}