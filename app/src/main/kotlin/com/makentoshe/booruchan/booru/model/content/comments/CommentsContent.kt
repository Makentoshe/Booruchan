package com.makentoshe.booruchan.booru.model.content.comments

import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.view.content.comments.vertical.pager.CommentsVerticalPagerFragment
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return createBuilder().build(contentViewModel.booru, appSettings)
    }

    private fun createBuilder(): ContentFragment.ContentFragmentBuilder<out ContentFragment> {
        return ContentFragment.newBuilder(CommentsVerticalPagerFragment::class.java)
    }

}
