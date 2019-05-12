package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import org.jetbrains.anko.find

/**
 * Controller for single grid element.
 *
 * @param listener is a preview downloading result listener.
 */
class GridElementController(
    private val listener: ImageDownloadListener,
    private val post: Post,
    private val streamListener: StreamDownloadListener
) {

    fun bindView(view: View) {
        val imageview = view.find<ImageView>(R.id.posts_page_gridview_element_image)
        val progressbar = view.find<ProgressBar>(R.id.posts_page_gridview_element_progress)

        setType(view)

        //preview was downloaded successfully
        listener.onSuccess {
            //tags image
            imageview.setImageBitmap(it)
            //hide progress
            progressbar.visibility = View.GONE
        }

        streamListener.onPartReceived { _, _, progress ->
            progressbar.progress = (progress * 100).toInt()
        }

        listener.onError {
            //tags error image
            val drawable = view.context.getDrawable(R.drawable.ic_alert_octagon_outline)!!
            drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            imageview.setImageDrawable(drawable)
            //hide progress
            progressbar.visibility = View.GONE

            it.printStackTrace()
        }
    }

    private fun setType(view: View) {
        val typeView = view.find<ImageView>(R.id.posts_page_gridview_element_type)
        ConcreteTypeControllerFactory(typeView).build(post).setType()
    }
}

