package com.makentoshe.booruchan.screen.samples.view

import android.view.Gravity
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class SamplePageWebmUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        frameLayout {
            id = R.id.samples_webm_container
            lparams(matchParent, matchParent) { gravity = Gravity.CENTER }
        }
    }
}