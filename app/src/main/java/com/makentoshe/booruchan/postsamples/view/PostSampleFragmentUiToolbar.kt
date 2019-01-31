package com.makentoshe.booruchan.postsamples.view

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
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

    private fun _RelativeLayout.downloadIcon() = frameLayout {
        id = R.id.postsample_toolbar_container_download
        setOnClickListener(::onDownloadIconClick)
        imageView {
            id = R.id.postsample_toolbar_container_download_icon
            setToolbarIcon(style, style.drawable.static.download)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun onDownloadIconClick(ignored: View) {
        val currentPage = viewModel.selectedPage
        viewModel.onFileImageLoadListener(currentPage) { post, result ->
            if (result.data != null) {
                viewModel.saveByteArrayAsImageFile(post.data!!, result.data, ignored.context)
            } else {
                TODO("Err")
            }
        }
    }
}