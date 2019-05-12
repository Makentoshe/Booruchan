package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import org.jetbrains.anko.find

class GridElementTypeController(private val post: Post) {

    fun bindView(view: View) {
        val typeView = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        ConcreteTypeControllerFactory(typeView).build(post).setType()
    }
}
