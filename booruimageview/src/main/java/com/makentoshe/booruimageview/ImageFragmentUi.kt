package com.makentoshe.booruimageview

import android.content.Context
import android.view.Gravity
import com.makentoshe.style.slidingUpPanel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager

class ImageFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = ui.slidingUpPanel {
        id = R.id.slidingPanel
        setGravity(Gravity.BOTTOM)
        shadowHeight = 0
        panelHeight = 0
        panelState = SlidingUpPanelLayout.PanelState.EXPANDED

        frameLayout { alpha = 0f }.lparams(matchParent, matchParent)

        viewPager {
            id = R.id.viewpager
        }.lparams(matchParent, matchParent)
    }
}
