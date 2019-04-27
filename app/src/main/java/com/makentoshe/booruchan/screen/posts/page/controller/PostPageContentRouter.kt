package com.makentoshe.booruchan.screen.posts.page.controller

import com.makentoshe.booruchan.navigation.Router

class PostPageContentRouter(
    private val router: Router,
    private val sampleScreenBuilder: SampleScreenBuilder
) {
    fun navigateToSampleScreen(position: Int) {
        val screen = sampleScreenBuilder.build(position)
        router.navigateTo(screen)
    }
}