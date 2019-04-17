package com.makentoshe.booruchan.screen.sampleinfo.view

import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.themedRecyclerView

class SampleInfoCommentsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            createMessageView()
            createProgressBar()
            createCommentsView()
        }
    }

    private fun _RelativeLayout.createProgressBar() = themedProgressBar(style.text) {
        id = R.id.sampleinfo_comments_progressbar
    }.lparams {
        centerInParent()
    }

    private fun _RelativeLayout.createMessageView() = themedTextView(style.text) {
        id = R.id.sampleinfo_comments_textview
        visibility = View.GONE
    }.lparams {
        gravity = Gravity.CENTER
        centerInParent()
    }

    private fun _RelativeLayout.createCommentsView() = themedRecyclerView(style.default) {
        id = R.id.sampleinfo_comments_recycleview
        visibility = View.GONE
    }.lparams(matchParent, matchParent)
}