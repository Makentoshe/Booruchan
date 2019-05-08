package com.makentoshe.booruchan.screen.booru

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.account.AccountScreen
import com.makentoshe.booruchan.screen.posts.container.PostsScreen

class LocalRouter(private val booru: Booru, private val tags: Set<Tag>, private val router: Router) {

    fun navigateToPosts() {
        val screen = PostsScreen(booru, tags)
        router.navigateWithReplace(screen)
    }

    fun navigateToAccount() {
        val screen = AccountScreen(booru)
        router.navigateWithReplace(screen)
    }

}