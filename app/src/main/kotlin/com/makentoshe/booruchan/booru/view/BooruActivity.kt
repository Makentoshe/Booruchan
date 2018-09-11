package com.makentoshe.booruchan.booru.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.makentoshe.booruchan.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.presenter.BooruPresenter
import org.jetbrains.anko.setContentView

class BooruActivity : Activity(), BooruView {

    private lateinit var slideableSearchLayout: SlideableSearchLayout
    private lateinit var presenter: BooruPresenter

    companion object {
        const val BOOR_EXTRA = "ExtraBoor"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = BooruPresenter(this)
        super.onCreate(savedInstanceState)
        val userInterface = BooruActivityUI(getAppSettings().getStyle(), presenter)
        slideableSearchLayout = userInterface
        userInterface.setContentView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val item = menu.add(Menu.NONE, R.id.action_show_search, Menu.NONE, "Show search view")
        item.setIcon(getAppSettings().getStyle().searchIcon)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_show_search) {
            slideableSearchLayout.showSearchLayout()
        }
        return true
    }

    override fun onBackPressed() {
        if (slideableSearchLayout.hideSearchLayout()) {
            return
        }
        super.onBackPressed()
    }

    override fun getContext(): Context {
        return this
    }

}