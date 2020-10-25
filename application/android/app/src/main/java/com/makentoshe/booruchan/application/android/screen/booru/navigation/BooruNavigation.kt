package com.makentoshe.booruchan.application.android.screen.booru.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.account.navigation.AccountScreen
import com.makentoshe.booruchan.application.android.screen.menu.navigation.MenuScreen
import com.makentoshe.booruchan.application.android.screen.posts.navigation.PostsScreen
import context.BooruContext
import ru.terrakok.cicerone.android.support.SupportAppScreen

class BooruNavigation(
    private val childFragmentManager: FragmentManager,
    private val booruContext: BooruContext
) {

    private var currentScreen: SupportAppScreen? = null

    fun navigateToPostsScreen() {
        val newScreen = PostsScreen(booruContext)
        navigateToScreen(newScreen)
    }

    fun navigateToAccountScreen() {
        val newScreen = AccountScreen(booruContext)
        navigateToScreen(newScreen)
    }

    fun navigateToMenuScreen() {
        val newScreen = MenuScreen(booruContext)
        navigateToScreen(newScreen)
    }

    private fun navigateToScreen(newScreen: SupportAppScreen) {
        // if screen already displaying
        if (currentScreen?.screenKey == newScreen.screenKey) return

        val currentFragment = childFragmentManager.findFragmentByTag(currentScreen?.screenKey)
        val newFragment = childFragmentManager.findFragmentByTag(newScreen.screenKey)
        if (newFragment != null) {
            // if contains fragment in backstack - show it
            replaceScreen(newFragment, currentFragment)
        } else {
            // if currently shows another fragment - hide it
            addScreen(newScreen, currentFragment)
        }

        currentScreen = newScreen
    }

    private fun replaceScreen(newFragment: Fragment, oldFragment: Fragment?) {
        val transaction = childFragmentManager.beginTransaction()
        if (oldFragment != null) transaction.hide(oldFragment)
        transaction.show(newFragment)
        transaction.commitNow()
    }

    private fun addScreen(newScreen: SupportAppScreen, oldFragment: Fragment?) {
        val transaction = childFragmentManager.beginTransaction()
        if (oldFragment != null) transaction.hide(oldFragment)
        transaction.add(R.id.fragment_booru_content, newScreen.fragment!!, newScreen.screenKey)
        transaction.commitNow()
    }
}