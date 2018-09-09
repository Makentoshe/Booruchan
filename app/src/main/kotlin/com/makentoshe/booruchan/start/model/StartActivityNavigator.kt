package com.makentoshe.booruchan.start.model

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.api.Boor
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.start.model.api.factory.Factory

class StartActivityNavigator(private val activity: AppCompatActivity) {

    fun startAppSettingsActivity() {
        activity.startActivity(Intent(activity, AppSettingsActivity::class.java))
    }

    fun startBooruActivity(serviceClass: Class<Boor>) {
        val boor = Factory.createServiceInstance(serviceClass).createServiceInstance()
        val intent = Intent(activity, BooruActivity::class.java)
        intent.putExtra(BooruActivity.BOOR_EXTRA, boor)
        activity.startActivity(intent)
    }
}