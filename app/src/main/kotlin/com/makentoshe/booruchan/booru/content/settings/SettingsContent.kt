package com.makentoshe.booruchan.booru.content.settings

import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.Content
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import com.makentoshe.booruchan.booru.content.settings.view.SettingsFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class SettingsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return ContentFragment.newBuilder(SettingsFragment::class.java).build(contentViewModel.booru, appSettings)
    }

}
