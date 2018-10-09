package com.makentoshe.booruchan.booru.content.users

import com.makentoshe.booruchan.booru.content.Content
import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import com.makentoshe.booruchan.booru.content.users.view.UsersFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings

class UsersContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): ContentFragment {
        return ContentFragment.newBuilder(UsersFragment::class.java).build(contentViewModel.booru, appSettings)
    }

}
