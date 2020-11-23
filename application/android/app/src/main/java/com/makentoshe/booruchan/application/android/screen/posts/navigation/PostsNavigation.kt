package com.makentoshe.booruchan.application.android.screen.posts.navigation

import com.makentoshe.booruchan.application.android.screen.samples.navigation.SampleScreen
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import ru.terrakok.cicerone.Router

class PostsNavigation(
    private val router: Router,
    private val booruContextClass: Class<BooruContext>
) {

    fun navigateToPostScreen(post: Post) {
        router.navigateTo(SampleScreen(post, booruContextClass))
    }
}
