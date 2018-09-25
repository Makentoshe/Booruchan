package com.makentoshe.booruchan.booru.view.content.comments

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView

class CommentsContentViewHolderUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {

        

        textView("SAS")
    }

}