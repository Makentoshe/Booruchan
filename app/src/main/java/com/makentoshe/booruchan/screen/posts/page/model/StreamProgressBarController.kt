package com.makentoshe.booruchan.screen.posts.page.model

import android.os.Build
import android.view.View
import android.widget.ProgressBar

class StreamProgressBarController(private val progressBar: ProgressBar) {

    fun onComplete() {
        progressBar.visibility = View.GONE
    }

    /**
     * Displays progress from 0 to 1.
     */
    fun onProgress(progress: Float) {
        val p = (progress * progressBar.max).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(p, true)
        } else {
            progressBar.progress = p
        }
    }

    fun onError() {
        progressBar.visibility = View.GONE
    }
}