package com.makentoshe.booruchan.screen.samples.controller

import android.view.View
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadEventListener
import org.jetbrains.anko.find

class SamplePageContentController(
    private val postsDownloadEventListener: PostsDownloadEventListener
) {

    fun bindView(view: View) {
        postsDownloadEventListener.onError {
            bindOnError(view, it)
        }
    }

    private fun bindOnError(view: View, throwable: Throwable) {
        //show message when items is out of stock
        if (throwable is IndexOutOfBoundsException) {
            bindOnError(view, Exception(view.context.getString(R.string.images_ran_out)))
            return
        }
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //and display a message
        val messageview = view.find<TextView>(R.id.samples_message)
        messageview.visibility = View.VISIBLE
        messageview.text = throwable.localizedMessage
        messageview.bringToFront()

    }
}