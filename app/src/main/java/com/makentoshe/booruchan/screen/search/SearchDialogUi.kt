package com.makentoshe.booruchan.screen.search

import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*

class SearchDialogUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            SearchDialogUiEditText().createView(AnkoContext.createDelegate(this))
            SearchDialogUiSeparator().createView(AnkoContext.createDelegate(this))
            SearchDialogUiChipGroup().createView(AnkoContext.createDelegate(this))
        }
    }
}

