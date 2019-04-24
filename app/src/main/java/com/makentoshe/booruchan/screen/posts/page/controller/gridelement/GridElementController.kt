package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadEventListener
import org.jetbrains.anko.find

/**
 * Controller for single grid element.
 *
 * @param listener is a preview downloading result listener.
 */
class GridElementController(
    private val listener: PostsPreviewImageDownloadEventListener,
    private val typeController: GridElementTypeController
) {

    fun bindView(view: View) {
        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)
        val progressbar = view.find<ProgressBar>(R.id.posts_page_gridview_element_progress)

        typeController.bindView(view)

        //preview was downloaded successfully
        listener.onSuccess {
            //set image
            imageview.setImageBitmap(it)
            //hide progress
            progressbar.visibility = View.GONE
        }

        listener.onError {
            //hide progress
            progressbar.visibility = View.GONE
        }
    }
}

