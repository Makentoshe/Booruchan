package com.makentoshe.booruchan.screen.samples.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*

class SamplePageUiMessage : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedTextView(style.default) {
            id = R.id.samples_message
            gravity = Gravity.CENTER
            visibility = View.GONE
            alpha = 0.6f
        }.lparams {
            centerInParent()
        }
    }
}