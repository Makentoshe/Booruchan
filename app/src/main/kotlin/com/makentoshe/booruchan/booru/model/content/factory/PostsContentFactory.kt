package com.makentoshe.booruchan.booru.model.content.factory

import android.arch.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.posts.PostsContent
import com.makentoshe.booruchan.booru.model.content.posts.PostsContentViewModel
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor

class PostsContentFactory(private val booru: Boor) : ContentFactory {

    override fun createContent(activity: Activity): Content {
        val viewModel = ViewModelProviders
                .of(activity, PostsContentViewModel.Factory(booru))[PostsContentViewModel::class.java]
        return PostsContent(viewModel, activity.getAppSettings())
    }


}