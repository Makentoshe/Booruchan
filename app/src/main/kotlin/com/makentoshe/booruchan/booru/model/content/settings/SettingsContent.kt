package com.makentoshe.booruchan.booru.model.content.settings

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.model.content.posts.PostOrderedInfinityViewModel
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.*

class SettingsContent(private val viewModel: PostOrderedInfinityViewModel,
                      private val appSettings: AppSettings) : Content {


    override fun createView(context: @AnkoViewDslMarker _FrameLayout, contentViewModel: ContentViewModel)
            : View = with(context) {

        textView("Settings Content")
    }

    override fun onSearchStarted(): (String?) -> (Unit) {
        return {}
    }
}
