package com.makentoshe.booruchan.booru.content.factory

import com.makentoshe.booruchan.booru.content.Content
import com.makentoshe.booruchan.booru.content.comments.CommentsContent
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor

class CommentContentFactory(private val booru: Boor) : ContentFactory {

    override fun createContent(activity: Activity): Content {
        return CommentsContent(activity.getAppSettings())
    }

}