package com.makentoshe.booruchan.start.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.start.presenter.StartActivityPresenter
import org.jetbrains.anko.*

class StartActivity : Activity(), StartActivityView {

    private lateinit var presenter: StartActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = StartActivityPresenter(this)
        super.onCreate(savedInstanceState)
        StartActivityUI(getAppSettings().getStyle(), presenter).setContentView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_start_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_app_settings -> {
                presenter.startAppSettingsActivity()
            }
        }
        return true
    }

    override fun getContext(): Context {
        return this
    }

    override fun getActivity(): AppCompatActivity {
        return this
    }
}
