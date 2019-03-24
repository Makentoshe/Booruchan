package com.makentoshe.booruchan.screen.search

import android.view.Gravity
import android.view.inputmethod.EditorInfo
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.view.delayAutoCompleteEditText
import org.jetbrains.anko.*

class SearchDialogUiEditText : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        delayAutoCompleteEditText(style.text) {
            id = R.id.searchDialog_delayAutocompleteEditText
            singleLine = true
            gravity = Gravity.TOP and Gravity.CENTER_HORIZONTAL
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            setPadding(dip(8), dip(10), dip(36), dip(8))
        }.lparams(matchParent, wrapContent) {
            alignParentTop()
        }
    }
}