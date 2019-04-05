package com.makentoshe.booruchan.screen.samples.view

import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class SamplePageUi : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            SamplePageUiContent().createView(AnkoContext.createDelegate(this))
            SamplePageUiMessage().createView(AnkoContext.createDelegate(this))
            SamplePageUiProgressBar().createView(AnkoContext.createDelegate(this))
        }
    }
}