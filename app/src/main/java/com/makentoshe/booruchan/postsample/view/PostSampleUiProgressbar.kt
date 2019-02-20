package com.makentoshe.booruchan.postsample.view

import android.graphics.PorterDuff
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.PostSampleViewModel
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSampleUiProgressbar(
    private val sampleDownloadController: SampleImageDownloadController,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        progressBar {
            id = R.id.postsample_progressbar
            isIndeterminate = true
            indeterminateDrawable?.setColorFilter(
                style.toolbar.getPrimaryColor(context),
                PorterDuff.Mode.SRC_ATOP
            )
            sampleDownloadController.onSampleLoadingFinished {
                visibility = View.GONE
            }
            sampleDownloadController.onSampleImageLoadingError {
                visibility = View.GONE
            }
        }.lparams {
            centerInParent()
        }
    }
}