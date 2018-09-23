package com.makentoshe.booruchan.booru.view

import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.BooruViewModelFactory
import com.makentoshe.booruchan.common.api.factory.Factory
import org.jetbrains.anko.setContentView

class BooruActivity : Activity() {

    companion object {
        const val BOORU_EXTRA = "ExtraBooru"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        createViewModel()
        super.onCreate(savedInstanceState)
        BooruActivityUI(getAppSettings().getStyle()).setContentView(this)
    }

    private fun createViewModel() {
        val boor = Factory.createFactory(intent.getStringExtra(BOORU_EXTRA)).createService()
        ViewModelProviders.of(this, BooruViewModelFactory(boor))[BooruViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val style = getAppSettings().getStyle()
        val item = menu.add(Menu.NONE, R.id.action_show_search, Menu.NONE, "Show search view")
        val icon = ContextCompat.getDrawable(this, style.avdFromCrossToMagnify)
        icon?.setColorFilter(ContextCompat.getColor(this, style.toolbarForegroundColor), PorterDuff.Mode.SRC_ATOP)
        item.icon = icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
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
        if (findViewById<View>(R.id.booru_content_alpha).visibility == View.VISIBLE) {
            ViewModelProviders.of(this)[BooruViewModel::class.java]
                    .hideSearchLabel(this, getAppSettings().getStyle())
            return
        }
        super.onBackPressed()
    }
}