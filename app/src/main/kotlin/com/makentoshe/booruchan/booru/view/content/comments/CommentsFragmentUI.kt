package com.makentoshe.booruchan.booru.view.content.comments

import android.view.View
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContentViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView

class CommentsFragmentUI(private val viewModel: CommentsContentViewModel)
    : AnkoComponent<CommentsFragment> {

    override fun createView(ui: AnkoContext<CommentsFragment>): View = with(ui) {
        textView("SAS")
    }




}