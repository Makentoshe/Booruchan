package com.makentoshe.booruchan.booru.model.content.factory

import com.makentoshe.booruchan.booru.model.content.Content
import com.makentoshe.booruchan.booru.view.BooruActivityUI
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor
import java.lang.IllegalArgumentException

interface ContentFactory {

    fun createContent(activity: Activity): Content

    companion object {

        fun createFactory(contentID: Int, booru: Boor): ContentFactory {
            return when (contentID) {
                0 -> PostOrderedInfinityContentFactory(booru)
                else -> PostOrderedInfinityContentFactory(booru)
//                BooruActivityUI.GALLERY_COMMENT -> CommentGalleryFactory()
//                else -> throw IllegalArgumentException()
            }
        }
    }

}