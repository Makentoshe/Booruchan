package com.makentoshe.booruchan.postsample.view

import android.view.View
import android.view.ViewManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.PostSampleViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class PostSampleUiContent(
    private val viewModel: PostSampleViewModel,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        subsamplingScaleImageView {
            id = R.id.postsample_imageview
            visibility = View.GONE
            backgroundColorResource = style.background.backgroundColorRes
            viewModel.onSampleDownloadedListener {
                setImage(ImageSource.bitmap(it))
                visibility = View.VISIBLE
            }
        }.lparams(matchParent, matchParent)
    }

    private fun ViewManager.subsamplingScaleImageView(init: SubsamplingScaleImageView.() -> Unit): SubsamplingScaleImageView {
        return ankoView({ SubsamplingScaleImageView(it) }, 0, init)
    }
}