package com.makentoshe.boorupostview

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.makentoshe.style.materialButton
import com.makentoshe.style.materialEditText
import org.jetbrains.anko.*

class PostsContentFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        val style = attr(com.makentoshe.style.R.attr.toolbar_style).data
        themedRelativeLayout(style) {
            lparams(matchParent, matchParent)

            createTagSearchEditText()
            createSearchButton()
        }
    }

    private fun _RelativeLayout.createTagSearchEditText() {
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        val editHeight = height / 3 * 2
        val hintColor = attr(android.R.attr.textColor).data
        materialEditText { editText ->
            editText.id = com.makentoshe.boorupostview.R.id.search_edit_text
            editText.hint = "blue_sky cloud 1girl"
            editText.singleLine = true
            editText.hintTextColor = hintColor
            editText.setPadding(dip(4), 0, 0, 0)
            editText.layoutParams = FrameLayout.LayoutParams(matchParent, editHeight)
            editText.imeOptions = EditorInfo.IME_ACTION_SEARCH
        }.lparams(matchParent, height) {
            alignParentTop()
            setMargins(dip(8), dip(10), dip(8), 0)
        }

    }

    private fun _RelativeLayout.createSearchButton() {
        val theme = attr(com.makentoshe.style.R.attr.text_button_style_material).data
        materialButton(theme) {
            id = com.makentoshe.boorupostview.R.id.search_button
            text = context.getString(R.string.start_search)
            elevation = 0f
            stateListAnimator = null
        }.lparams(matchParent, dip(56)) {
            alignParentBottom()
        }
    }
}
