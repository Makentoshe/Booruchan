package com.makentoshe.booruchan.booru.model.content.comments

import android.view.View
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.model.content.posts.PostOrderedInfinityViewModel
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.*

class CommentsContent(private val viewModel: CommentsContentViewModel,
                      private val appSettings: AppSettings) : Content {


    override fun createView(context: @AnkoViewDslMarker _FrameLayout, contentViewModel: ContentViewModel)
            : View = with(context) {

        textView("Comments Content")
    }

    override fun onSearchStarted(): (String?) -> (Unit) {
        return {}
    }
}
