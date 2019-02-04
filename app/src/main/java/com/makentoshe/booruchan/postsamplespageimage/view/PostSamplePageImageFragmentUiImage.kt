package com.makentoshe.booruchan.postsamplespageimage.view

import android.graphics.Bitmap
import android.view.View
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragmentViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._RelativeLayout
import org.jetbrains.anko.matchParent

class PostSamplePageImageFragmentUiImage(
    private val viewModel: PostSamplePageImageFragmentViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        subsamplingScaleImageView {
            id = R.id.sample_image_view
            setMinimumDpi(100)
            viewModel.onFinishSampleImageLoadingListener {
                onFinishSampleImageLoading(it)
            }
        }.lparams(matchParent, matchParent)
    }

    private fun SubsamplingScaleImageView.onFinishSampleImageLoading(result: DownloadResult<Bitmap>) {
        visibility = if (result.data != null) {
            setImage(ImageSource.bitmap(result.data))
            View.VISIBLE
        } else View.GONE
    }

    private fun _RelativeLayout.subsamplingScaleImageView(
        action: SubsamplingScaleImageView.() -> Unit
    ): SubsamplingScaleImageView {
        val view = SubsamplingScaleImageView(context).apply(action)
        addView(view)
        return view
    }
}