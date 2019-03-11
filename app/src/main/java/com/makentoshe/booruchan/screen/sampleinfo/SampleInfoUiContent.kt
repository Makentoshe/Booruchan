package com.makentoshe.booruchan.screen.sampleinfo

import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class SampleInfoUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        viewPager {
            id = R.id.sampleinfo_viewpager
        }.lparams(matchParent, matchParent) {
            below(R.id.sampleinfo_toolbar)
        }
    }
}