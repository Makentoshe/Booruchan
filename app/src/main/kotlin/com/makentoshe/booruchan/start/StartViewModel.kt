package com.makentoshe.booruchan.start

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.ListAdapter
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.start.model.ServiceListGenerator
import com.makentoshe.booruchan.start.model.StartNavigator

class StartViewModel: ViewModel() {

    private val serviceListGenerator: ServiceListGenerator by lazy {
        ServiceListGenerator()
    }

    private val navigator: StartNavigator by lazy {
        StartNavigator()
    }

    fun createAdapter(context: Context): ListAdapter {
        return serviceListGenerator.createAdapter(context, serviceListGenerator.servicesList)
    }

    fun clickOnService(activity: AppCompatActivity, serviceName: String) {
        navigator.startBooruActivity(activity, serviceName)
    }

    fun clickOnOverflow(position: Int, activity: AppCompatActivity) {
        when (position) {
            R.id.action_app_settings -> {
                navigator.startAppSettingsActivity(activity)
            }
        }
    }

}