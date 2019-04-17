package com.makentoshe.booruchan.screen.sampleinfo.view

import android.content.Context
import android.view.View
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7._CardView
import org.jetbrains.anko.cardview.v7.cardView

class CommentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        cardView {
            lparams(matchParent) { margin = dip(8) }
            radius = 0f
            elevation = dip(8).toFloat()
            createContent()
        }
    }

    private fun _CardView.createContent() = relativeLayout {
        createMeta()
        createBody()
    }

    private fun _RelativeLayout.createMeta() = relativeLayout {
        id = R.id.comment_meta
        createAuthor()
        createDate()
    }.lparams { alignParentTop() }

    private fun _RelativeLayout.createAuthor() = textView {
        id = R.id.comment_author
        padding = dip(8)
    }.lparams {
        alignParentStart()
    }

    private fun _RelativeLayout.createDate() = textView {
        id = R.id.comment_date
        padding = dip(8)
    }.lparams {
        alignParentEnd()
    }

    private fun _RelativeLayout.createBody() = textView {
        id = R.id.comment_body
        padding = dip(8)
    }.lparams { below(R.id.comment_meta) }
}