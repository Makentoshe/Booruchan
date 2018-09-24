package com.makentoshe.booruchan.booru.model.content.comments

import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.CommentsFragment
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return CommentsFragment()
    }

}
