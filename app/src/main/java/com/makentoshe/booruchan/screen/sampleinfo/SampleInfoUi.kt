package com.makentoshe.booruchan.screen.sampleinfo

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class SampleInfoUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = Booruchan.INSTANCE.style.background.backgroundColorRes
            SampleInfoUiToolbar().createView(AnkoContext.createDelegate(this))
            SampleInfoUiContent().createView(AnkoContext.createDelegate(this))
            AnkoProgressBar(R.id.sampleinfo_progressbar).createView(AnkoContext.createDelegate(this))
        }
    }
}