package com.makentoshe.booruchan.start.model

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.common.api.factory.Factory

class StartNavigator {

    fun startBooruActivity(activity: AppCompatActivity, serviceName: String) {
        val intent = Intent(activity, BooruActivity::class.java)
        intent.putExtra(BooruActivity.BOORU_EXTRA, serviceName)
        activity.startActivity(intent)
    }

    fun startAppSettingsActivity(activity: AppCompatActivity) {
        activity.startActivity(Intent(activity, AppSettingsActivity::class.java))
    }

}