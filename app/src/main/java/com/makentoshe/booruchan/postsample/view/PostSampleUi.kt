package com.makentoshe.booruchan.postsample.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsample.model.DownloadErrorController
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.booruchan.postsamples.FullScreenController
import com.makentoshe.style.Style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.relativeLayout

class PostSampleUi(
    private val sampleDownloadController: SampleImageDownloadController,
    private val downloadErrorController: DownloadErrorController,
    private val fullScreenController: FullScreenController
) : AnkoComponent<Fragment> {

    private val style: Style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            PostSampleUiProgressbar(sampleDownloadController, style)
                .createView(AnkoContext.createDelegate(this))
            PostSampleUiContent(downloadErrorController, sampleDownloadController, fullScreenController)
                .createView(AnkoContext.createDelegate(this))
            PostSampleUiMessageview(sampleDownloadController, style)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}