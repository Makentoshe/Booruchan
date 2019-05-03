package com.makentoshe.booruchan.screen.samples.view

import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class SampleSwipeUi : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            SampleSwipeUiContent()
                .createView(AnkoContext.createDelegate(this))
            SampleSwipeUiBottombar()
                .createView(AnkoContext.createDelegate(this))
        }
    }
}