package com.makentoshe.booruchan.postsamples.view

import android.view.View
import com.makentoshe.booruchan.postsamples.PostSampleFragment
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

class PostSampleFragmentUi(private val viewModel: PostsSampleFragmentViewModel) : AnkoComponent<PostSampleFragment> {
    override fun createView(ui: AnkoContext<PostSampleFragment>): View = with(ui) {
        verticalLayout {
            PostSampleFragmentUiToolbar(viewModel).createView(AnkoContext.createDelegate(this))
            PostSampleFragmentUiContent(viewModel, ui.owner.childFragmentManager)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}