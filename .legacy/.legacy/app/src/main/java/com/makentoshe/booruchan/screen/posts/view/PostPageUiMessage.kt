package com.makentoshe.booruchan.screen.posts.view

import android.view.Gravity
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostPageUiMessage : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        textView {
            id = R.id.posts_page_textview
            gravity = Gravity.CENTER
        }.lparams {
            centerInParent()
        }
    }
}