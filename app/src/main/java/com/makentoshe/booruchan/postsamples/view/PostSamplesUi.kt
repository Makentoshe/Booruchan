package com.makentoshe.booruchan.postsamples.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsamples.FullScreenController
import com.makentoshe.booruchan.postsamples.model.ViewPagerAdapterBuilder
import com.makentoshe.booruchan.postsamples.model.DownloadFileController
import com.makentoshe.booruchan.postsamples.model.NavigationController
import com.makentoshe.style.Style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class PostSamplesUi(
    private val adapterBuilder: ViewPagerAdapterBuilder,
    private val downloadFileController: DownloadFileController,
    private val navigationController: NavigationController,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            PostSamplesUiToolbar(downloadFileController, style)
                .createView(AnkoContext.createDelegate(this))
            PostSamplesUiContent(ui.owner.childFragmentManager, adapterBuilder, navigationController)
                .createView(AnkoContext.createDelegate(this))
            PostSamplesUiBottombar(navigationController, style)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}