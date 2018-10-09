package com.makentoshe.booruchan.booru.content.posts

import com.makentoshe.booruchan.booru.content.Content
import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.view.PostsFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class PostsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return createBuilder().build(contentViewModel.booru, appSettings)
    }

    private fun createBuilder(): ContentFragment.ContentFragmentBuilder<out ContentFragment> {
        return ContentFragment.newBuilder(PostsFragment::class.java)
    }
}