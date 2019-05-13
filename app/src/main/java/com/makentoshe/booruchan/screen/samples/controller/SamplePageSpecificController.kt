package com.makentoshe.booruchan.screen.samples.controller

import android.view.View
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.download.DownloadListener
import org.jetbrains.anko.find

/**
 * Controller for a specific sample page.
 * Displays error message when downloading was failed and a [T] otherwise.
 */
abstract class SamplePageSpecificController<T>(protected val listener: DownloadListener<T>) {

    fun bindView(view: View) {
        listener.onSuccess { bindOnSuccess(view, it) }
        listener.onError { bindOnError(view, it) }
    }

    protected abstract fun bindOnSuccess(view: View, t: T)

    protected open fun bindOnError(view: View, throwable: Throwable) {
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