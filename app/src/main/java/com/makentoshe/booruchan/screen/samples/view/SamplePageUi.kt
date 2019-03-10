package com.makentoshe.booruchan.screen.samples.view

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
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

class SamplePageUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        relativeLayout {
            SamplePageUiContentImage().createView(AnkoContext.createDelegate(this))
        }
    }
}

class SamplePageUiContentImage : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        imageView {
            id = R.id.samples_image
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }
}