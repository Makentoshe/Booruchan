package com.makentoshe.booruchan.screen.samples.view

import android.view.View
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class SamplePageUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        frameLayout {
            id = R.id.samples_content
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
        imageView {
            id = R.id.samples_preview
            visibility = View.GONE
        }.lparams(matchParent, matchParent) {
            centerInParent()
        }
    }
}