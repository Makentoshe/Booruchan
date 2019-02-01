package com.makentoshe.booruchan.postsamples.view

import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.view.setToolbarIcon
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import org.jetbrains.anko.*

class PostSampleFragmentUiToolbar(private val viewModel: PostsSampleFragmentViewModel) : AnkoComponent<LinearLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = dip(56))
            id = R.id.postsample_toolbar_container
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()
            downloadIcon()
        }
    }

    private fun _RelativeLayout.downloadIcon() = relativeLayout {
        id = R.id.postsample_toolbar_container_download

        //set ripple effect
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        backgroundResource = outValue.resourceId

        setOnClickListener(::onDownloadIconClick)
        imageView {
            id = R.id.postsample_toolbar_container_download_icon
            setToolbarIcon(style, style.drawable.static.download)
        }.lparams(dip(24), dip(24)) {
            centerInParent()
        }

        progressBar {
            id = R.id.postsample_toolbar_container_download_progressbar
            visibility = View.GONE
            viewModel.onFileDownloadListener {
                visibility = View.GONE
            }
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }

    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onDownloadIconClick(view: View) {
        view.findViewById<ProgressBar>(R.id.postsample_toolbar_container_download_progressbar).visibility = View.VISIBLE
        val currentPage = viewModel.selectedPage
        viewModel.onFileImageLoadListener(currentPage) { post, result ->
            if (result.data != null) {
                viewModel.saveByteArrayAsImageFile(post.data!!, result.data, view.context)
            } else {
                TODO("Err")
            }
        }
    }
}