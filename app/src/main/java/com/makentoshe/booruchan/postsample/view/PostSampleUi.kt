package com.makentoshe.booruchan.postsample.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postsample.PostSampleViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.relativeLayout

class PostSampleUi(
    private val viewModel: PostSampleViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            PostSampleUiProgressbar(viewModel, style)
                .createView(AnkoContext.createDelegate(this))
            PostSampleUiContent(viewModel, style)
                .createView(AnkoContext.createDelegate(this))
            PostSampleUiMessageview(viewModel, style)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}