package com.makentoshe.booruchan.screen.booru.model

import com.makentoshe.booruchan.navigation.Router
import ru.terrakok.cicerone.Cicerone

class LocalNavigator {

    private val localCicerone = Cicerone.create(Router())

    private val router = localCicerone.router

    fun navigateToPosts() = Unit

    fun navigateToAccount() = Unit
}