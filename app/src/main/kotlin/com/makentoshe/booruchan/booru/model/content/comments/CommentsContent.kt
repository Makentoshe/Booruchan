package com.makentoshe.booruchan.booru.model.content.comments

import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.view.content.comments.CommentsFragment
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: com.makentoshe.booruchan.booru.ContentViewModel): ContentFragment {
        return ContentFragment.new(CommentsFragment::class.java).build(contentViewModel.booru, appSettings)
    }

}
