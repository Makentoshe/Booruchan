package com.makentoshe.booruchan.screen.search

import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.component.DelayAutocompleteEditTextComponent
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._RelativeLayout
import org.jetbrains.anko.alignParentTop

class SearchDialogUiEditText : AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = DelayAutocompleteEditTextComponent()
        .createView(AnkoContext.createDelegate(ui.owner)).apply {
            id = R.id.searchDialog_delayAutocompleteEditText
            updateLayoutParams<RelativeLayout.LayoutParams> {
                alignParentTop()
            }
        }
}