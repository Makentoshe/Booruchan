package com.makentoshe.booruchan.application.android.screen.samples.model

import android.view.View
import android.widget.ProgressBar
import com.google.android.exoplayer2.Player

class SampleVideoPlayerEventListener(
    private val progressBar: ProgressBar
) : Player.EventListener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        progressBar.visibility = when (playbackState) {
            Player.STATE_BUFFERING -> View.VISIBLE
            Player.STATE_READY -> View.GONE
            else -> return
        }
    }
}