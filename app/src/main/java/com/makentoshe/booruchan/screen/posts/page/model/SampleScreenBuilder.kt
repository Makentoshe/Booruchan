package com.makentoshe.booruchan.screen.posts.page.model

import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.model.TagsHolder
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.samples.SampleScreen

class SampleScreenBuilder(
    private val booruHolder: BooruHolder,
    private val tagsHolder: TagsHolder
) {
    fun build(position: Int): Screen {
        return SampleScreen(position, booruHolder.booru, tagsHolder.tags)
    }
}