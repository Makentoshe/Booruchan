package com.makentoshe.booruchan.booru.model.content.posts

import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.booru.view.content.posts.PostsFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class PostsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return ContentFragment.new(PostsFragment::class.java).build(contentViewModel.booru, appSettings)
    }
}