package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadEventListener
import org.jetbrains.anko.find

/**
 * Controller for single grid element.
 *
 * @param listener is a preview downloading result listener.
 */
class PostPageGridElementController(private val listener: PostsPreviewImageDownloadEventListener) {
    fun bindView(view: View) {
        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)

        //preview was downloaded successfully
        listener.onSuccess {
            imageview.setImageBitmap(it)
        }
    }
}

