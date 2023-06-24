package com.makentoshe.booruchan.application.android.screen.booru.navigation

import android.view.MenuItem
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.account.navigation.AccountScreen
import com.makentoshe.booruchan.application.android.screen.menu.navigation.MenuScreen
import com.makentoshe.booruchan.application.android.screen.posts.navigation.PostsScreen
import com.makentoshe.booruchan.core.context.BooruContext
import ru.terrakok.cicerone.android.support.SupportAppScreen

class BottomNavigationScreenFactory(private val booruContext: BooruContext) {

    fun build(item: MenuItem): SupportAppScreen = when (item.itemId) {
        R.id.booru_navigation_account -> AccountScreen(booruContext)
        R.id.booru_navigation_posts -> PostsScreen(booruContext)
        R.id.booru_navigation_menu -> MenuScreen(booruContext)
        else -> throw NoSuchElementException(item.toString())
    }
}