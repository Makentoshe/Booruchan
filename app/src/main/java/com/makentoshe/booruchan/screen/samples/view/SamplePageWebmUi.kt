package com.makentoshe.booruchan.screen.samples.view

import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class SamplePageWebmUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent) { gravity = Gravity.CENTER }

            videoView {
                id = R.id.samples_webm
                visibility = View.GONE
            }.lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }
        }
    }
}