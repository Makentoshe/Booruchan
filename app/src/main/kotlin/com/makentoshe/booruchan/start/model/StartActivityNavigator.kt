package com.makentoshe.booruchan.start.model

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity

class StartActivityNavigator(private val activity: AppCompatActivity) {

    fun startAppSettingsActivity() {
        activity.startActivity(Intent(activity, AppSettingsActivity::class.java))
    }

    fun startBooruActivity(position: Int, generateListOfServices: List<String>) {
        when (position) {
            0 -> Toast.makeText(activity, "Start ${generateListOfServices[0]} activity", Toast.LENGTH_LONG).show()
        }
    }
}