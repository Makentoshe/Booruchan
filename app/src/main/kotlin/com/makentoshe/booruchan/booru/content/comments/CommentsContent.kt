package com.makentoshe.booruchan.booru.content.comments

import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.Content
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.view.CommentsFragment
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return createBuilder().build(contentViewModel.booru, appSettings)
    }

    private fun createBuilder(): ContentFragment.ContentFragmentBuilder<out ContentFragment> {
        return ContentFragment.newBuilder(CommentsFragment::class.java)
    }

}
