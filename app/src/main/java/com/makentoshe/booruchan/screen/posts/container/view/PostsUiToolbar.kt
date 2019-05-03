package com.makentoshe.booruchan.screen.posts.container.view

import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style
import com.makentoshe.booruchan.style.getColorFromStyle
import org.jetbrains.anko.*

class PostsUiToolbar : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        themedRelativeLayout(style.toolbar) {
            elevation = dip(4).toFloat()
            createToolbarView()
            createDrawerIcon().also { it.visibility = View.GONE }
            createSearchIcon()
        }
    }

    private fun _RelativeLayout.createSearchIcon() = themedFrameLayout(style.toolbar) {
        id = R.id.posts_toolbar_search
        lparams(dip(56), dip(56))

        themedImageView(style.toolbar) {
            setImageResource(R.drawable.avd_cross_magnify)
            setColorFilter(getColorFromStyle(android.R.attr.textColorPrimary), PorterDuff.Mode.SRC_ATOP)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun _RelativeLayout.createDrawerIcon() = themedFrameLayout(style.toolbar) {
        id = R.id.booru_toolbar_drawermenu
        themedImageView(style.toolbar) {
            setImageResource(R.drawable.ic_menu_vector)
            setColorFilter(getColorFromStyle(android.R.attr.textColorPrimary), PorterDuff.Mode.SRC_ATOP)
        }.lparams(dip(24), dip(24)) {
            gravity = Gravity.CENTER
        }
    }.lparams(dip(56), dip(56)) {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    private fun _RelativeLayout.createToolbarView() = themedToolbar(style.toolbar) {
        id = R.id.booru_toolbar
    }.lparams(width = matchParent) {
        alignWithParent = true
        rightOf(R.id.booru_toolbar_drawermenu)
    }
}