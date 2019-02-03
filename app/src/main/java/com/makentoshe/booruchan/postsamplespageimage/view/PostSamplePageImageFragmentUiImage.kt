package com.makentoshe.booruchan.postsamplespageimage.view

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.Style
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragmentViewModel
import org.jetbrains.anko.*

class PostSamplePageImageFragmentUiImage(
    private val viewModel: PostSamplePageImageFragmentViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        imageView {
            id = R.id.sample_image_view
            viewModel.onFinishSampleImageLoadingListener {
                onFinishSampleImageLoading(it)
            }
        }.lparams(matchParent, matchParent)
    }

    private fun ImageView.onFinishSampleImageLoading(result: DownloadResult<Bitmap>) {
        visibility = if (result.data != null) {
            setImageBitmap(result.data)
            View.VISIBLE
        } else View.GONE
    }
}