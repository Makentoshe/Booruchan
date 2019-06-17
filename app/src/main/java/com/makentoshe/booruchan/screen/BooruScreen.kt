package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.boorupostview.PostsFragmentNavigator
import com.makentoshe.booruview.BooruTransitionData
import com.makentoshe.booruview.BooruFragment
import com.makentoshe.booruview.BooruFragmentNavigator
import java.io.Serializable

/**
 * Class describes a a booru screen.
 */
class BooruScreen(private val booruTransitionData: BooruTransitionData, private val navigator: BooruFragmentNavigator) : Screen() {

    /** Factory property creates */
    override val fragment: Fragment
        get() = BooruFragment.build(booruTransitionData, navigator)
}

/**
 * Class performs a local navigation.
 * It replaces a screens by each other and does not contain back command.
 */
class BooruFragmentNavigator(
    cicerone: ru.terrakok.cicerone.Cicerone<Router>,
    private val navigator: PostsFragmentNavigator
) : BooruFragmentNavigator, Serializable {

    private val holder = cicerone.navigatorHolder

    private val router = cicerone.router

    override fun setNavigator(navigator: ru.terrakok.cicerone.Navigator) {
        holder.setNavigator(navigator)
    }

    override fun removeNavigator() = holder.removeNavigator()

    override fun navigateToPosts(booruTransitionData: BooruTransitionData) {
        router.replaceScreen(PostsScreen(booruTransitionData.booru, booruTransitionData.tags, navigator))
    }

    override fun navigateToAccount() {
        println("Account")
    }
}

