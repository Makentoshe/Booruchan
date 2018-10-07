package com.makentoshe.booruchan.booru.content.factory

import com.makentoshe.booruchan.booru.content.Content
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor

interface ContentFactory {

    fun createContent(activity: Activity): Content

    companion object {

        fun createFactory(contentID: Int, booru: Boor): ContentFactory {
            return when (contentID) {
                0 -> PostsContentFactory(booru)
                1 -> CommentContentFactory(booru)
                2 -> UsersContentFactory(booru)
                3 -> SettingsContentFactory(booru)
                else -> throw IllegalArgumentException()
            }
        }
    }

}