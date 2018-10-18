package com.makentoshe.booruchan.booru.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import com.makentoshe.booruchan.booru.panel.PanelViewModel
import com.makentoshe.booruchan.common.api.factory.Factory
import com.makentoshe.booruchan.sample.view.SampleActivity
import org.jetbrains.anko.setContentView
import java.lang.StringBuilder

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
        icon?.setColorFilter(ContextCompat.getColor(this, style.toolbar.onPrimaryColorRes), PorterDuff.Mode.SRC_ATOP)
        item.icon = icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    private fun removeOptionsMenu(menu: Menu) {
        menu.removeItem(R.id.action_show_search)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                val tags = data?.getStringArrayExtra(SampleActivity.RESULT)!!
                (supportFragmentManager.fragments[0] as ContentFragment).apply {
                    val builder = StringBuilder()
                    tags.forEach { builder.append(it).append(" ") }
                    findViewById<EditText>(R.id.booru_toolbar_search_delayautocompleteedittext).setText(builder)
                    onSearchStarted().invoke(builder.toString())
                }
            }
        }
    }
}