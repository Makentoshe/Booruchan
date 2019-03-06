package com.makentoshe.booruchan.postsamples.view

import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.view.setToolbarIcon
import com.makentoshe.booruchan.postsamples.model.DownloadFileController
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSamplesUiToolbar(
    private val downloadFileController: DownloadFileController,
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.postsamples_toolbar
            backgroundColorResource = style.toolbar.primaryColorRes
            createDownloadIcon()
        }.lparams(matchParent, dip(56)) {
            alignParentTop()
        }
    }


    private fun _RelativeLayout.createDownloadIcon() = frameLayout {
        id = R.id.postsamples_toolbar_download
//        setOnClickListener {
//            println("Click")
//            val currentItem = uiParent.findViewById<ViewPager>(R.id.postsamples_content_viewpager).currentItem
//            downloadFileController.startDownload(it, currentItem)
//        }
        imageView {
            id = R.id.postsamples_toolbar_download_icon
            setToolbarIcon(style, style.drawable.static.download)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

}