package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostSamplesViewModel
import com.makentoshe.style.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class PostSamplesUi(
    private val viewModel: PostSamplesViewModel,
    private val style: Style = Booruchan.INSTANCE.style
) : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            PostSamplesUiToolbar(style).createView(AnkoContext.createDelegate(this))
            verticalViewPager {
                id = R.id.postsamples_verticalpager
                adapter = viewModel.verticalViewPagerAdapter
                currentItem = 1
            }.lparams(matchParent, matchParent) {
                below(R.id.postsamples_toolbar)
            }
        }
    }

    private fun ViewManager.verticalViewPager(init: @AnkoViewDslMarker VerticalViewPager.() -> Unit): VerticalViewPager {
        return ankoView({ VerticalViewPager(it) }, 0, init)
    }
}

class PostSamplesUiToolbar(
    private val style: Style
) : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        relativeLayout {
            id = R.id.postsamples_toolbar
            backgroundColorResource = style.toolbar.primaryColorRes
        }.lparams(matchParent, dip(56))
    }
}

