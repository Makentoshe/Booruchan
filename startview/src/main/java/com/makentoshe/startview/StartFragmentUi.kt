package com.makentoshe.startview

import android.content.Context
import android.graphics.PorterDuff
import android.view.Gravity
import org.jetbrains.anko.*

/** StartFragment user interface */
class StartFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        themedRelativeLayout {
            createToolbarView()

            listView {
                id = R.id.start_content_listview
            }.lparams {
                below(R.id.start_toolbar)
            }
        }
    }

    private fun _RelativeLayout.createToolbarView() {
        val style = attr(com.makentoshe.style.R.attr.toolbar_style)
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        themedRelativeLayout(style.data) {
            id = R.id.start_toolbar
            elevation = dip(10).toFloat()

            themedToolbar(style.data) {
                titleResource = R.string.app_name
            }.lparams(width = matchParent) { alignWithParent = true }

            createToolbarOverflowIconView()

        }.lparams(matchParent, height)
    }

    private fun _RelativeLayout.createToolbarOverflowIconView() {
        val color = colorAttr(android.R.attr.textColorPrimary)
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        frameLayout {
            id = R.id.start_toolbar_overflow

            imageView {
                setImageResource(R.drawable.ic_overflow)
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }.lparams(dip(24), dip(24)) {
                gravity = Gravity.CENTER
            }

        }.lparams(height, height) {
            alignParentEnd()
            centerVertically()
        }
    }
}