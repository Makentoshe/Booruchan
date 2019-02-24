package com.makentoshe.booruchan.posttags.view

import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*

class PostTagsUiMessage(private val message: CharSequence) :
    AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        textView(message).lparams {
            gravity = Gravity.CENTER
            centerInParent()
        }
    }
}