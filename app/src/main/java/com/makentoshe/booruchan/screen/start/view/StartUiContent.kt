package com.makentoshe.booruchan.screen.start.view

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style.style
import org.jetbrains.anko.*

class StartUiContent : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        themedListView(style.main) {
            id = R.id.start_content_listview
        }.lparams {
            below(R.id.start_toolbar)
        }
    }
}