package com.makentoshe.booruchan.start

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.StyleableAnkoComponent
import com.makentoshe.booruchan.appsettings.AppSettingsActivity
import com.makentoshe.booruchan.styles.AstarteStyle
import org.jetbrains.anko.*

class StartActivity : AppCompatActivity() {

    val style = AstarteStyle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartActivityView(style).setContentView(this)
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
