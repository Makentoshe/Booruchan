package com.makentoshe.booruchan.screen.samples.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.AnkoProgressBar
import org.jetbrains.anko.*

class SamplePageUi : AnkoComponent<Fragment> {

    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            AnkoProgressBar(R.id.samples_progress).createView(AnkoContext.createDelegate(this))
            SamplePageUiMessage().createView(AnkoContext.createDelegate(this))
            SamplePageUiContent().createView(AnkoContext.createDelegate(this))
        }
    }
}