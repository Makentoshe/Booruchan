package com.makentoshe.booruchan.booru.model.content.factory

import android.arch.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContent
import com.makentoshe.booruchan.booru.model.content.posts.PostOrderedInfinityViewModel
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor

class CommentContentFactory(private val booru: Boor) : ContentFactory {

    override fun createContent(activity: Activity): Content {
        val viewModel = ViewModelProviders
                .of(activity, PostOrderedInfinityViewModel
                        .PostOrderedInfinityViewModelFactory(booru))[PostOrderedInfinityViewModel::class.java]
        return CommentsContent(viewModel, activity.getAppSettings())
    }

}