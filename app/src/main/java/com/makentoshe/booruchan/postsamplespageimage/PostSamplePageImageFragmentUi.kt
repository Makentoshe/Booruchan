package com.makentoshe.booruchan.postsamplespageimage

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageView

class PostSamplePageImageFragmentUi(
    private val viewModel: PostSamplePagePreviewFragmentViewModel
) : AnkoComponent<PostSamplePageImageFragment> {
    override fun createView(ui: AnkoContext<PostSamplePageImageFragment>): View = with(ui) {
        imageView {
            viewModel.subscribe {
                setImageBitmap(it)
            }
        }
    }

}