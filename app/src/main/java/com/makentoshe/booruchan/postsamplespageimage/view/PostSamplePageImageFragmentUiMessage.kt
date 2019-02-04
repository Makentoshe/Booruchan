package com.makentoshe.booruchan.postsamplespageimage.view

import android.graphics.Bitmap
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragmentViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSamplePageImageFragmentUiMessage(
    private val viewModel: PostSamplePageImageFragmentViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        textView {
            id = R.id.sample_image_message
            visibility = View.GONE
            gravity = Gravity.CENTER

            viewModel.onFinishSampleImageLoadingListener {
                onFinishSampleImageLoading(it)
            }

            setOnClickListener { onClick(ui.owner) }

        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }

    private fun TextView.onFinishSampleImageLoading(result: DownloadResult<Bitmap>) {
        if (result.data != null) return
        visibility = View.VISIBLE
        val text = StringBuilder(context.getString(R.string.sample_download_error)).append("\n")
        if (result.exception != null) text.append(result.exception.message).append("\n")
        text.append(context.getString(R.string.tap_for_retry))
        this.text = text
    }

    private fun onClick(root: View) {
        val messageview = root.findViewById<TextView>(R.id.sample_image_message)
        val progressbar = root.findViewById<ProgressBar>(R.id.sample_image_progressbar)
        val imageview = root.findViewById<ImageView>(R.id.sample_image_view)

        messageview.visibility = View.GONE
        progressbar.visibility = View.VISIBLE

        viewModel.startSampleImageLoading()
    }
}