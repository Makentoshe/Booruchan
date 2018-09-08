package com.makentoshe.booruchan.start

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.makentoshe.booruchan.Activity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import org.jetbrains.anko.*

class StartActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartActivityView(getAppSettings().getStyle()).setContentView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_start_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_app_settings -> {
                startActivity(Intent(this, AppSettingsActivity::class.java))
            }
        }
        return true
    }
}
