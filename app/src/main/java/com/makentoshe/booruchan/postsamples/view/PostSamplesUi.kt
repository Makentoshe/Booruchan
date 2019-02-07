package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.VerticalViewPager
import com.makentoshe.booruchan.postsamples.model.SamplesVerticalViewPagerAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class PostSamplesUi : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        verticalViewPager {
            id = R.id.postsamples_verticalpager
            val fragmentManager = ui.owner.childFragmentManager
            adapter = SamplesVerticalViewPagerAdapter(fragmentManager)
            currentItem = 1
        }
    }

    private fun ViewManager.verticalViewPager(init: @AnkoViewDslMarker VerticalViewPager.() -> Unit): VerticalViewPager {
        return ankoView({ VerticalViewPager(it) }, 0, init)
    }

}

