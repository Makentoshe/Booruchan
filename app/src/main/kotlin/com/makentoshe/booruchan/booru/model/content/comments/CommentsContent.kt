package com.makentoshe.booruchan.booru.model.content.comments

import android.support.v4.app.Fragment
import android.view.View
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.CommentsFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.*

class CommentsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): Fragment {
        return CommentsFragment()
    }

    override fun onSearchStarted(): (String?) -> (Unit) {
        return {}
    }
}
