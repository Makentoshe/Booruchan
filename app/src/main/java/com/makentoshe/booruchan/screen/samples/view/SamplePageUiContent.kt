package com.makentoshe.booruchan.screen.samples.view

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._RelativeLayout
import org.jetbrains.anko.relativeLayout

class SamplePageUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        relativeLayout {
            SamplePageUiContentImage()
                .createView(AnkoContext.createDelegate(this))
            SamplePageUiContentGif()
                .createView(AnkoContext.createDelegate(this))
            SamplePageUiContentWebm()
                .createView(AnkoContext.createDelegate(this))
        }
    }
}