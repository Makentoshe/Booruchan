package com.makentoshe.booruview

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import java.io.Serializable

class BooruFragmentNavigator(cicerone: Cicerone<Router>) : BooruPanelFragmentNavigator, Serializable {

    private val holder = cicerone.navigatorHolder

    private val router = cicerone.router

    fun setNavigator(navigator: Navigator) = holder.setNavigator(navigator)

    fun removeNavigator() = holder.removeNavigator()

    override fun navigateToPosts() {
        println("Posts")
    }

    override fun navigateToAccount() {
        println("Account")
    }
}