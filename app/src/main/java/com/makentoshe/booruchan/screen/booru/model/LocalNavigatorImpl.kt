package com.makentoshe.booruchan.screen.booru.model

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.account.AccountScreen
import com.makentoshe.booruchan.screen.posts.PostsScreen
import ru.terrakok.cicerone.Cicerone

class LocalNavigatorImpl(private val booru: Booru, private val tags: Set<Tag>) : LocalNavigator {

    private val cicerone = Cicerone.create(Router())

    override fun setNavigator(navigator: FragmentNavigator) {
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun removeNavigator() {
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun navigateToPosts() {
        val screen = PostsScreen(booru, tags)
        cicerone.router.replaceScreen(screen)
    }

    override fun navigateToAccount() {
        val screen = AccountScreen(booru)
        cicerone.router.replaceScreen(screen)
    }
}