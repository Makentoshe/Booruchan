package com.makentoshe.booruchan.screen.search

import android.R
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.view.component.SeparatorComponent
import org.jetbrains.anko.*

class SearchDialogUiSeparator : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>): View =
        with(ui.owner) {
        SeparatorComponent(R.color.darker_gray)
            .createView(AnkoContext.createDelegate(this)).apply {
            id = com.makentoshe.booruchan.R.id.searchDialog_separator
            updateLayoutParams<RelativeLayout.LayoutParams> {
                below(com.makentoshe.booruchan.R.id.searchDialog_delayAutocompleteEditText)
                setMargins(dip(8), 0, 0, 0)
            }
        }
    }
}