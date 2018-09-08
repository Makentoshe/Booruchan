package com.makentoshe.booruchan.start.presenter

import android.widget.ListAdapter
import com.makentoshe.booruchan.start.model.ServicesGenerator
import com.makentoshe.booruchan.start.model.StartActivityNavigator
import com.makentoshe.booruchan.start.view.StartActivityView

class StartActivityPresenter(activityView: StartActivityView) {

    private val servicesGenerator = ServicesGenerator(activityView.getContext())
    private val navigator = StartActivityNavigator(activityView.getActivity())

    fun generateListOfServices(): List<String> {
        return servicesGenerator.generateList()
    }

    fun createAdapter(): ListAdapter {
        return servicesGenerator.createAdapter(generateListOfServices())
    }

    fun onServicesListItemClick(position: Int) {
        navigator.startBooruActivity(position, generateListOfServices())
    }

    fun startAppSettingsActivity() {
        navigator.startAppSettingsActivity()
    }

}