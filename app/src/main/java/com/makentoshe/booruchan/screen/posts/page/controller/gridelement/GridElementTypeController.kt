package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import org.jetbrains.anko.find
import java.io.File

class GridElementTypeController(private val post: Post) {

    fun bindView(view: View) {

        when (File(post.fileUrl).extension) {
            "webm" -> videoType(view)
            "gif" -> animationType(view)
            else -> defaultType(view)
        }
    }

    private fun videoType(view: View) {
        val videoDrawable = view.context.getDrawable(R.drawable.ic_video)!!
        view.setType(videoDrawable)
    }

    private fun animationType(view: View) {
        val animationDrawable = view.context.getDrawable(R.drawable.ic_animation)!!
        view.setType(animationDrawable)
    }

    private fun defaultType(view: View) {
        val typeview = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        typeview.visibility = View.GONE
    }

    private fun View.setType(typeDrawable: Drawable) {
        val typeview = find<ImageView>(R.id.posts_page_gridview_element_type)
        typeDrawable.mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
        typeview.setImageDrawable(typeDrawable)
    }
}