package com.makentoshe.booruchan.screen.samples.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.themedViewPager

class SampleUi : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        themedViewPager(style.default) {
            id = R.id.samples_container_viewpager
        }
    }
}