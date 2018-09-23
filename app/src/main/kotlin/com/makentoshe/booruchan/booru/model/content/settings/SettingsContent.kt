package com.makentoshe.booruchan.booru.model.content.settings

import android.support.v4.app.Fragment
import android.view.View
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.SettingsFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.*

class SettingsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): Fragment {
        return SettingsFragment()
    }


    override fun onSearchStarted(): (String?) -> (Unit) {
        return {}
    }
}
