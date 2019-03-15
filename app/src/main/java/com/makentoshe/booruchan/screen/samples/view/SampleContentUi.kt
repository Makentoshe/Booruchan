package com.makentoshe.booruchan.screen.samples.view

import android.graphics.Color
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.relativeLayout

class SampleContentUi : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            backgroundColor = Color.TRANSPARENT
            SampleContentUiContent()
                .createView(AnkoContext.createDelegate(this))
            SampleContentUiBottombar()
                .createView(AnkoContext.createDelegate(this))
        }
    }
}