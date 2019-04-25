package com.makentoshe.booruchan.screen.samples.swipe.view

import android.graphics.Color
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.verticalViewPager
import org.jetbrains.anko.*

class SampleContentUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        verticalViewPager {
            backgroundColor = Color.TRANSPARENT
            id = R.id.samples_container
//            setPadding(0, 0, 0, dip(56))
        }.lparams(matchParent, matchParent) {
            above(R.id.samples_bottombar)
        }
    }
}