package com.makentoshe.boorusamplesview.view

import android.content.Context
import android.view.Gravity
import com.makentoshe.boorusamplesview.R
import com.makentoshe.style.slidingUpPanel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager

/**
 * User interface for [ImageFragment] component.
 */
class BaseFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = ui.slidingUpPanel {
        id = R.id.slidingPanel
        // scroll to bottom
        setGravity(Gravity.BOTTOM)
        // disable shadow
        shadowHeight = 0
        // disable panel toolbar
        panelHeight = 0
        // panel will be in foreground at the startup
        panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        // transparent background
        frameLayout { alpha = 0f }.lparams(matchParent, matchParent)
        // foreground
        viewPager { id = R.id.viewpager }.lparams(matchParent, matchParent)
    }
}
