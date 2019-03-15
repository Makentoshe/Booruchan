package com.makentoshe.booruchan.screen.sampleinfo.view

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.AnkoProgressBar
import org.jetbrains.anko.*

class SampleInfoUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            SampleInfoUiToolbar().createView(AnkoContext.createDelegate(this))
            SampleInfoUiContent().createView(AnkoContext.createDelegate(this))
            AnkoProgressBar(R.id.sampleinfo_progressbar).createView(AnkoContext.createDelegate(this))
        }
    }
}