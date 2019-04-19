package com.makentoshe.booruchan.screen.booru

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.account.AccountScreen
import com.makentoshe.booruchan.screen.posts.PostsScreen

class LocalRouter(private val booru: Booru, private val tags: Set<Tag>) {

    fun navigateToPosts(router: Router) {
        val screen = PostsScreen(booru, tags)
        router.navigateWithReplace(screen)
    }

    fun navigateToAccount(router: Router) {
        val screen = AccountScreen(booru)
        router.navigateWithReplace(screen)
    }

}