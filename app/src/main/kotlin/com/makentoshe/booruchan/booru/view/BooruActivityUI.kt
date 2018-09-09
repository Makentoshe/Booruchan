package com.makentoshe.booruchan.booru.view

import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.StyleableAnkoComponent
import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.support.v4._DrawerLayout
import org.jetbrains.anko.support.v4.drawerLayout

class BooruActivityUI(style: Style) : StyleableAnkoComponent<BooruActivity>(style) {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui) {
        drawerLayout = drawerLayout {
            lparams(width = matchParent, height = matchParent)
            createContentView()
            createPanelView()
        }
        setupHamburgerIcon(ui)
        return@with drawerLayout
    }

    private fun _DrawerLayout.createPanelView() {
        frameLayout {
            backgroundResource = style.backgroudColor
            textView {
                text = "Panel"
            }
        }.lparams {
            width = dip(260)
            height = matchParent
            gravity = Gravity.START
        }

    }

    private fun _DrawerLayout.createContentView() {
        frameLayout {
            createToolbar()
            textView {
                text = "Content"
            }
        }.lparams(matchParent, matchParent)
    }

    private fun _FrameLayout.createToolbar() {
        toolbar = toolbar {
            setTitleTextColor(ContextCompat.getColor(context, style.toolbarForegroundColor))
            title = "Gelbooru"
            backgroundColorResource = style.toolbarBackgroundColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = dip(4).toFloat()
            }
        }.lparams {
            width = matchParent
            height = dip(style.dpToolbarHeight)
        }
    }

    private fun setupHamburgerIcon(ui: AnkoContext<BooruActivity>) {
        ui.owner.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val toggle = ActionBarDrawerToggle(ui.owner, drawerLayout, toolbar,
                    R.string.drawer_open, R.string.drawer_close)
            toggle.isDrawerIndicatorEnabled = true
            toggle.drawerArrowDrawable.color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ui.ctx.resources.getColor(style.toolbarForegroundColor, theme)
            } else {
                ui.ctx.resources.getColor(style.toolbarForegroundColor)
            }
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

}