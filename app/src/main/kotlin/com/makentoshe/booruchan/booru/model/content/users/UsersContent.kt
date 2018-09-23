package com.makentoshe.booruchan.booru.model.content.users

import android.support.v4.app.Fragment
import android.view.View
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.UsersFragment
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.*

class UsersContent(private val appSettings: AppSettings) : Content {

    override fun createView(contentViewModel: ContentViewModel): Fragment {
        return UsersFragment()
    }


    override fun onSearchStarted(): (String?) -> (Unit) {
        return {}
    }
}
