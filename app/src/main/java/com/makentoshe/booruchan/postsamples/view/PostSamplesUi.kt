package com.makentoshe.booruchan.postsamples.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsamples.model.DownloadFileController
import com.makentoshe.booruchan.postsamples.PostSamplesViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*

class PostSamplesUi(
    private val viewModel: PostSamplesViewModel,
    private val downloadFileController: DownloadFileController,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            PostSamplesUiToolbar(downloadFileController, style).createView(AnkoContext.createDelegate(this))
            PostSamplesUiContent(ui.owner.childFragmentManager, viewModel).createView(AnkoContext.createDelegate(this))
            PostSamplesUiBottombar(viewModel, style).createView(AnkoContext.createDelegate(this))
        }
    }
}