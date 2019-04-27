package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadEventListener
import org.jetbrains.anko.find

/**
 * Controller for single grid element.
 *
 * @param listener is a preview downloading result listener.
 */
class GridElementController(
    private val listener: PreviewImageDownloadEventListener,
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
            //set error image
            val drawable = view.context.getDrawable(R.drawable.ic_alert_octagon_outline)!!
            drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            imageview.setImageDrawable(drawable)
            //hide progress
            progressbar.visibility = View.GONE

            it.printStackTrace()
        }
    }
}

