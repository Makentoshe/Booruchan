package com.makentoshe.booruchan.screen.booru.model

import com.makentoshe.booruchan.navigation.FragmentNavigator

interface LocalNavigator {

    fun setNavigator(navigator: FragmentNavigator)

    fun removeNavigator()

    fun navigateToPosts()

    fun navigateToAccount()
}