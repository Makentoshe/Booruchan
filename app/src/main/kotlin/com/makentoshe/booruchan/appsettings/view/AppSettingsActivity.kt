package com.makentoshe.booruchan.appsettings.view

import android.os.Bundle
import android.view.MenuItem
import com.makentoshe.booruchan.common.Activity
import org.jetbrains.anko.setContentView

class AppSettingsActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSettingsActivityUI(getAppSettings().getStyle()).setContentView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
               true
            }
            else -> false
        }
    }
}