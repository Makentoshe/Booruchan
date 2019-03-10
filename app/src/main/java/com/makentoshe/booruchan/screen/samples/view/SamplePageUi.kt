package com.makentoshe.booruchan.screen.samples.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.*

class SamplePageUi : AnkoComponent<Fragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            SamplePageUiProgress().createView(AnkoContext.createDelegate(this))
            SamplePageUiMessage().createView(AnkoContext.createDelegate(this))
            SamplePageUiContent().createView(AnkoContext.createDelegate(this))
        }
    }
}