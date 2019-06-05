package com.makentoshe.booruview.navigation

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.navigation.Navigator
import com.makentoshe.navigation.Router
import ru.terrakok.cicerone.Cicerone
import java.io.Serializable

class BooruFragmentNavigator(
    cicerone: Cicerone<Router>,
    private val booru: Booru,
    private val tags: Set<Tag>
) : BooruPanelFragmentNavigator, Serializable {

    private val holder = cicerone.navigatorHolder

    private val router = cicerone.router

    fun setNavigator(navigator: Navigator) = holder.setNavigator(navigator)

    fun removeNavigator() = holder.removeNavigator()

    override fun navigateToPosts() = router.replaceScreen(PostsScreen(booru, tags))

    override fun navigateToAccount() {
        println("Account")
    }
}