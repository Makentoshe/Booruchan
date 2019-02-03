package com.makentoshe.booruchan.postsamplespageimage.view

import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.Style
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragment
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragmentViewModel
import org.jetbrains.anko.*

class PostSamplePageImageFragmentUi(
    private val viewModel: PostSamplePageImageFragmentViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<PostSamplePageImageFragment> {

    override fun createView(ui: AnkoContext<PostSamplePageImageFragment>): View = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            PostSamplePageImageFragmentUiProgress(viewModel, style).createView(AnkoContext.createDelegate(this))
            PostSamplePageImageFragmentUiMessage(viewModel, style).createView(AnkoContext.createDelegate(this))
            PostSamplePageImageFragmentUiImage(viewModel, style).createView(AnkoContext.createDelegate(this))
        }
    }
}

