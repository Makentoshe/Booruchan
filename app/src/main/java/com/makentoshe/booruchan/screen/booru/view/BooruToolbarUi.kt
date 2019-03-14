package com.makentoshe.booruchan.screen.booru.view

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style.style
import org.jetbrains.anko.*

open class BooruToolbarUi : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        themedRelativeLayout(style.toolbar) {
            elevation = dip(4).toFloat()
            createToolbarIcon()
            createToolbarView()
        }
    }

    private fun _RelativeLayout.createToolbarIcon() {
        val id = R.id.booru_toolbar_drawermenu

        BooruToolbarUiDrawer(id)
            .createView(AnkoContext.createDelegate(this))
            .updateLayoutParams<RelativeLayout.LayoutParams> {
                addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                addRule(RelativeLayout.CENTER_VERTICAL)
            }
    }

    private fun _RelativeLayout.createToolbarView() = themedToolbar(style.toolbar) {
        id = R.id.booru_toolbar
    }.lparams(width = matchParent) {
        alignWithParent = true
        rightOf(R.id.booru_toolbar_drawermenu)
    }
}