package com.makentoshe.booruchan.screen.search

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.chipGroup
import org.jetbrains.anko.*

class SearchDialogUiChipGroup : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        chipGroup {
            id = R.id.searchDialog_chipgroup
        }.lparams(matchParent) {
            setMargins(dip(8), dip(8), dip(8), dip(8))
            below(R.id.searchDialog_separator)
        }
    }
}