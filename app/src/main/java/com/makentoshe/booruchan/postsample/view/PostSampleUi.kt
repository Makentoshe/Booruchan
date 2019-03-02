package com.makentoshe.booruchan.postsample.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsample.model.DownloadErrorController
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.style.Style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.relativeLayout

class PostSampleUi(
    private val sampleDownloadController: SampleImageDownloadController,
    private val downloadErrorController: DownloadErrorController,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            PostSampleUiProgressbar(sampleDownloadController, style).createView(AnkoContext.createDelegate(this))
            PostSampleUiContent(
                downloadErrorController, sampleDownloadController, style
            ).createView(AnkoContext.createDelegate(this))
            PostSampleUiMessageview(sampleDownloadController, style).createView(AnkoContext.createDelegate(this))
        }
    }
}