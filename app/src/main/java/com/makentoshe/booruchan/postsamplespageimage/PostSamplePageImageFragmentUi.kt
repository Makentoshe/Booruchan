package com.makentoshe.booruchan.postsamplespageimage

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostSamplePageImageFragmentUi(
    private val viewModel: PostSamplePageImageFragmentViewModel
) : AnkoComponent<PostSamplePageImageFragment> {

    private val style = Booruchan.INSTANCE.style
    private var shouldBeReloaded = false

    override fun createView(ui: AnkoContext<PostSamplePageImageFragment>): View = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            progressBar()
            messageTextView()
            imageView {
                id = R.id.sample_image_view
                visibility = View.GONE
                viewModel.subscribe {
                    onDownloadFinished(it, this@relativeLayout)
                }
            }.lparams(matchParent, matchParent)

            setOnClickListener(::reloadClick)
        }
    }

    private fun _RelativeLayout.progressBar() = progressBar {
        id = R.id.sample_image_progressbar
        isIndeterminate = true
        val indeterminateDrawable = indeterminateDrawable.mutate()
        indeterminateDrawable.setColorFilter(style.background.getOnBackgroundColor(context), PorterDuff.Mode.SRC_IN)
        this.indeterminateDrawable = indeterminateDrawable
    }.lparams {
        centerInParent()
    }

    private fun _RelativeLayout.messageTextView() = textView {
        id = R.id.sample_image_message
        visibility = View.GONE
        gravity = Gravity.CENTER
    }.lparams {
        centerInParent()
    }

    private fun onDownloadFinished(result: DownloadResult<Bitmap>, root: View) {
        val progressbar = root.findViewById<ProgressBar>(R.id.sample_image_progressbar)
        val messageview = root.findViewById<TextView>(R.id.sample_image_message)
        val imageview = root.findViewById<ImageView>(R.id.sample_image_view)

        progressbar.visibility = View.GONE

        if (result.data == null) {
            messageview.visibility = View.VISIBLE
            val text = StringBuilder(messageview.context.getString(R.string.sample_download_error))
            if (result.exception != null) {
                text.append("\n").append(result.exception.message)
            }
            messageview.text = text
            shouldBeReloaded = true
        } else {
            imageview.visibility = View.VISIBLE
            imageview.setImageBitmap(result.data)
        }
    }

    private fun reloadClick(view: View) {
        val progressbar = view.findViewById<ProgressBar>(R.id.sample_image_progressbar)
        val messageview = view.findViewById<TextView>(R.id.sample_image_message)
        val imageview = view.findViewById<ImageView>(R.id.sample_image_view)

        if (shouldBeReloaded) {
            shouldBeReloaded = false
            messageview.visibility = View.GONE
            progressbar.visibility = View.VISIBLE
            imageview.apply { viewModel.subscribe { onDownloadFinished(it, view) } }
        }
    }
}