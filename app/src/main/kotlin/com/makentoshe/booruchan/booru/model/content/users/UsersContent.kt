package com.makentoshe.booruchan.booru.model.content.users

import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.booru.view.content.UsersFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class UsersContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return ContentFragment.newBuilder(UsersFragment::class.java).build(contentViewModel.booru, appSettings)
    }

}
