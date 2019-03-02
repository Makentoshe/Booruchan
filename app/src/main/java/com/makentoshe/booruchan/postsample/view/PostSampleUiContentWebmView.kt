package com.makentoshe.booruchan.postsample.view

import android.net.Uri
import android.view.View
import android.view.ViewManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.custom.ankoView

class PostSampleUiContentWebmView(private val webmUrl: String) : AnkoComponent<_FrameLayout> {
    override fun createView(ui: AnkoContext<_FrameLayout>): View = with(ui.owner) {
        playerView {
            player = initPlayer(webmUrl)
        }
    }

    private fun ViewManager.playerView(init: PlayerView.() -> Unit): PlayerView {
        return ankoView({ PlayerView(it) }, 0, init)
    }

    /* Inits an ExoPlayer for displaying a webm file.*/
    private fun PlayerView.initPlayer(url: String): ExoPlayer {
        val userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(context, userAgent)
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))
        return ExoPlayerFactory.newSimpleInstance(context).apply { prepare(mediaSource) }
    }
}