package com.makentoshe.booruchan.postsample.view

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSampleUiMessageview(
    private val downloadController: SampleImageDownloadController,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        textView {
            id = R.id.postsample_messageview
            gravity = Gravity.CENTER
            visibility = View.GONE

            downloadController.onSampleLoadingFinished{
                visibility = View.GONE
            }

            downloadController.onSampleImageLoadingError {
                val sb = StringBuilder(context.getString(R.string.sample_download_error)).append("\n")
                sb.append(it).append("\n")
                sb.append(context.getString(R.string.tap_for_retry))
                text = sb
                visibility = View.VISIBLE
            }

//            setOnClickListener {
//                val progressbar = ui.owner.findViewById<ProgressBar>(R.id.postsample_progressbar)
//                progressbar.visibility = View.VISIBLE
//                it.visibility = View.GONE
//
//                viewModel.loadPosts(viewModel.pagePosition)
//            }

        }.lparams(matchParent, matchParent)
    }
}