package com.makentoshe.booruview

import ru.terrakok.cicerone.NavigatorHolder
import java.io.Serializable

/**
 * Performs local navigation.
 */
interface BooruFragmentNavigator : Serializable, NavigatorHolder {

    /** Replace current screen to the posts */
    fun navigateToPosts(booruTransitionData: BooruTransitionData)

    /** Replace current screen to the accounts */
    fun navigateToAccount()
}