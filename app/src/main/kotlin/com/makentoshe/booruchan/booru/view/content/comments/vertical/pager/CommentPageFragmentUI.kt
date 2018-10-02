package com.makentoshe.booruchan.booru.view.content.comments.vertical.pager

import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*

class CommentPageFragmentUI(private val page: Int, private val color: Int) : AnkoComponent<CommentPageFragment> {
    override fun createView(ui: AnkoContext<CommentPageFragment>): View = with(ui) {
        textView {
            gravity = Gravity.CENTER
            text = page.toString()
            backgroundColor = color
        }
    }

}