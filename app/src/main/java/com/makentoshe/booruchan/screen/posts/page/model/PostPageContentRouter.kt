package com.makentoshe.booruchan.screen.posts.page.model

import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.posts.page.model.SampleScreenBuilder

class PostPageContentRouter(
    private val router: Router,
    private val sampleScreenBuilder: SampleScreenBuilder
) {
    fun navigateToSampleScreen(position: Int) {
        val screen = sampleScreenBuilder.build(position)
        router.navigateTo(screen)
    }
}