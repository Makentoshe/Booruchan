package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.view.ViewManager
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.model.SamplesContentViewPagerAdapter
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.viewPager

class PostSamplesContentUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        viewPager{
            id = R.id.postsamples_content_viewpager
            val fragmentManager = ui.owner.childFragmentManager
            adapter = SamplesContentViewPagerAdapter(fragmentManager)
        }
    }
}