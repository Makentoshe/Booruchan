package com.makentoshe.booruchan.screen.sampleinfo.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.samples.view.SamplePageUiProgressBar
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.view.AnkoProgressBar
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.themedRelativeLayout

class SampleInfoUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        themedRelativeLayout(style.default) {
            SampleInfoUiToolbar().createView(AnkoContext.createDelegate(this))
            SampleInfoUiContent().createView(AnkoContext.createDelegate(this))
            SamplePageUiProgressBar().createView(AnkoContext.createDelegate(this))
//            AnkoProgressBar(R.id.sampleinfo_progressbar).createView(AnkoContext.createDelegate(this))
        }
    }
}