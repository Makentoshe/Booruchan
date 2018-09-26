package com.makentoshe.booruchan.booru.model.content.settings

import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.booru.view.content.SettingsFragment
import com.makentoshe.booruchan.booru.view.content.UsersFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class SettingsContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return ContentFragment.new(SettingsFragment::class.java).build(contentViewModel.booru, appSettings)
    }

}
