package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostSamplesViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView

class PostSamplesUi(private val viewModel: PostSamplesViewModel) : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        verticalViewPager {
            id = R.id.postsamples_verticalpager
            adapter = viewModel.verticalViewPagerAdapter
            currentItem = 1
        }
    }

    private fun ViewManager.verticalViewPager(init: @AnkoViewDslMarker VerticalViewPager.() -> Unit): VerticalViewPager {
        return ankoView({ VerticalViewPager(it) }, 0, init)
    }

}

