package com.makentoshe.booruchan.screen

import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.drawerlayout.widget.DrawerLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.Inflater
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

open class ToolbarIcon(
    @IdRes private val id: Int,
    @DrawableRes private val icon: Int,
    private val color: Int
) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View =
        with(ui.owner) {
            frameLayout {
                id = this@ToolbarIcon.id
                lparams(dip(56), dip(56))

                imageView {
                    setImageResource(icon)
                    setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                }.lparams(dip(24), dip(24)) {
                    gravity = Gravity.CENTER
                }
            }
        }

}

open class BooruToolbarUiInflater(private val drawer: DrawerLayout) : Inflater {
    override fun inflate(view: View) {
        val icon = view.findViewById<View>(R.id.booru_toolbar_drawermenu)
        icon.setOnClickListener { onClick(drawer) }
    }

    private fun onClick(view: DrawerLayout) {
        if (view.isDrawerOpen(GravityCompat.START)) {
            view.closeDrawer(GravityCompat.START)
        } else {
            view.openDrawer(GravityCompat.START)
        }
    }
}

open class BooruToolbarUi : AnkoComponent<ViewGroup> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        relativeLayout {
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()
            createToolbarIcon()
            createToolbarView()
        }
    }

    private fun _RelativeLayout.createToolbarIcon() {
        val id = R.id.booru_toolbar_drawermenu
        val icon = style.drawable.static.menu
        val color = style.toolbar.getOnPrimaryColor(context)
        ToolbarIcon(id, icon, color)
            .createView(AnkoContext.createDelegate(this))
            .updateLayoutParams<RelativeLayout.LayoutParams> {
                addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                addRule(RelativeLayout.CENTER_VERTICAL)
            }
    }

    private fun _RelativeLayout.createToolbarView() = toolbar {
        id = R.id.booru_toolbar
        setTitleTextColor(style.toolbar.getOnPrimaryColor(context))
        setBackgroundResource(style.toolbar.primaryColorRes)
        setSubtitleTextColor(style.toolbar.getOnPrimaryColor(context))
    }.lparams(width = matchParent) {
        alignWithParent = true
        rightOf(R.id.booru_toolbar_drawermenu)
    }
}