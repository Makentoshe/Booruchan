package com.makentoshe.booruchan.sample.view

import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.sample.SampleViewModel
import com.makentoshe.booruchan.sample.model.SearchIconBehaviour
import org.jetbrains.anko.setContentView

class SampleActivity: Activity() {

    companion object {
        const val BOORU_EXTRA = "Booru"
        const val START_ID = "Start Id"
        const val TAGS_EXTRA = "Tags"
    }

    private lateinit var viewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = SampleViewModel.Factory(intent)
        viewModel = ViewModelProviders.of(this, factory)[SampleViewModel::class.java]
        super.onCreate(savedInstanceState)
        SampleActivityUI(getAppSettings().getStyle(), viewModel).setContentView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        createSearchOptionMenu(menu)
        return true
    }

    private fun createSearchOptionMenu(menu: Menu) {
        val style = getAppSettings().getStyle()
        val item = menu.add(Menu.FIRST, R.id.action_show_search, Menu.NONE, "Start search by tags")
        val icon = ContextCompat.getDrawable(this, style.avdFromCrossToMagnify)
        icon?.setColorFilter(ContextCompat.getColor(this, style.toolbar.onPrimaryColorRes), PorterDuff.Mode.SRC_ATOP)
        item.icon = icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        viewModel.searchIconBehaviour = SearchIconBehaviour(item)
        item.isVisible = false
    }

}