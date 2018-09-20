package com.makentoshe.booruchan.booru.model.content.factory

import android.arch.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.users.UsersContent
import com.makentoshe.booruchan.booru.model.content.users.UsersContentViewModel
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor

class UsersContentFactory(private val booru: Boor) : ContentFactory {

    override fun createContent(activity: Activity): Content {
        val viewModel = ViewModelProviders.of(activity,
                UsersContentViewModel.Factory(booru))[UsersContentViewModel::class.java]
        return UsersContent(viewModel, activity.getAppSettings())
    }

}