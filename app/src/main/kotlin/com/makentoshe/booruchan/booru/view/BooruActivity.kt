package com.makentoshe.booruchan.booru.view

import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.PanelViewModel
import com.makentoshe.booruchan.common.api.factory.Factory
import org.jetbrains.anko.setContentView

class BooruActivity : Activity() {

    companion object {
        const val BOORU_EXTRA = "ExtraBooru"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        createViewModels()
        super.onCreate(savedInstanceState)
        BooruActivityUI(getAppSettings().getStyle()).setContentView(this)
    }

    private fun createViewModels() {
        val booru = Factory.createFactory(intent.getStringExtra(BOORU_EXTRA)).createService()
        ViewModelProviders.of(this, PanelViewModel.Factory(booru))[PanelViewModel::class.java]
        ViewModelProviders.of(this, ContentViewModel.Factory(booru))[ContentViewModel::class.java]
        ViewModelProviders.of(this)[BooruViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        ViewModelProviders.of(this)[PanelViewModel::class.java]
                .addSelectedItemPositionObserver(this) {
                    when (it!!) {
                        0 -> createSearchOptionMenu(menu)
                        else -> removeOptionsMenu(menu)
                    }
                }
        return true
    }

    private fun createSearchOptionMenu(menu: Menu) {
        val style = getAppSettings().getStyle()
        val item = menu.add(Menu.FIRST, R.id.action_show_search, Menu.NONE, "Show search view")
        val icon = ContextCompat.getDrawable(this, style.avdFromCrossToMagnify)
        icon?.setColorFilter(ContextCompat.getColor(this, style.toolbarForegroundColor), PorterDuff.Mode.SRC_ATOP)
        item.icon = icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    private fun removeOptionsMenu(menu: Menu) {
        menu.removeGroup(Menu.FIRST)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_show_search -> {
                ViewModelProviders.of(this)[BooruViewModel::class.java]
                        .changeSearchLabelState(this, getAppSettings().getStyle())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.booru_main)
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
            return
        }
        if (findViewById<View>(R.id.booru_content_alpha).visibility == View.VISIBLE) {
            ViewModelProviders.of(this)[BooruViewModel::class.java]
                    .hideSearchLabel(this, getAppSettings().getStyle())
            return
        }
        super.onBackPressed()
    }
}