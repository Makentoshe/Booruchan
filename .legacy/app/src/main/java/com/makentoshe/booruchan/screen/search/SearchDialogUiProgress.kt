package com.makentoshe.booruchan.screen.search

import android.graphics.PorterDuff
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import org.jetbrains.anko.*

class SearchDialogUiProgress : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        themedProgressBar(style.progress) {
            visibility = View.GONE
            id = R.id.search_progress
            isIndeterminate = true
            setPadding(dip(4), dip(4), dip(4), dip(4))
            indeterminateDrawable.setColorFilter(
                getColorFromStyle(android.R.attr.colorAccent),
                PorterDuff.Mode.SRC_ATOP
            )
        }.lparams {
            centerInParent()
            alignParentTop()
            alignParentRight()
            above(R.id.searchDialog_separator)
        }
    }
}