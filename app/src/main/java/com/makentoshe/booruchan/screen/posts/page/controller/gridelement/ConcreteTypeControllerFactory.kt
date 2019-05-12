package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.widget.ImageView
import com.makentoshe.booruchan.api.component.post.Post
import java.io.File

class ConcreteTypeControllerFactory(private val typeView: ImageView) {
    fun build(post: Post) = when (File(post.fileUrl).extension) {
        "webm" -> WebmTypeController(typeView)
        "gif" -> GifTypeController(typeView)
        else -> ImageTypeController(typeView)
    }
}