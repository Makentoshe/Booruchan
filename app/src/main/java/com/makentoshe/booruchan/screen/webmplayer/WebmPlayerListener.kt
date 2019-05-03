package com.makentoshe.booruchan.screen.webmplayer

import android.view.View
import com.google.android.exoplayer2.Player
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

class WebmPlayerListener(view: View) : Player.EventListener {

    private val progressBarView: View by lazy { view.find<View>(R.id.samples_progress) }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == 3) {
            progressBarView.visibility = View.GONE
        } else {
            progressBarView.visibility = View.VISIBLE
        }
    }
}