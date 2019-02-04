package com.makentoshe.booruchan.postsamplespageimage.view

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.view.View
import android.widget.ProgressBar
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragmentViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSamplePageImageFragmentUiProgress(
    private val viewModel: PostSamplePageImageFragmentViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        progressBar {
            id = R.id.sample_image_progressbar
            isIndeterminate = true
            val indeterminateDrawable = indeterminateDrawable.mutate()
            indeterminateDrawable.setColorFilter(
                style.background.getOnBackgroundColor(context),
                PorterDuff.Mode.SRC_IN
            )
            this.indeterminateDrawable = indeterminateDrawable

            viewModel.onFinishSampleImageLoadingListener {
                onFinishSampleImageLoading(it)
            }

        }.lparams {
            centerInParent()
        }
    }

    private fun ProgressBar.onFinishSampleImageLoading(result: DownloadResult<Bitmap>) {
        visibility = View.GONE
    }
}