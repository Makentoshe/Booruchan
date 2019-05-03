package com.makentoshe.booruchan.screen.samples.controller

import android.os.Handler
import android.os.Looper
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.view.CircularProgressBar
import org.jetbrains.anko.find

class CircularProgressBarController(private val streamListener: StreamDownloadListener) {

    fun bindView(view: View) {
        val progressview = view.find<CircularProgressBar>(R.id.samples_progress_concrete)

        streamListener.onPartReceived { _, _, progress ->
            progressview.setProgressView((100 * progress).toInt())
        }
    }

    private fun CircularProgressBar.setProgressView(progress: Int) {
        Handler(Looper.getMainLooper()).post { setProgress(progress) }
    }
}