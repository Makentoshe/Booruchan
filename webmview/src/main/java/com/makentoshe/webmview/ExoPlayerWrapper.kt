package com.makentoshe.webmview

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/** Wraps and encapsulates an ExoPLayer instance */
class ExoPlayerWrapper(private val player: ExoPlayer, private val context: Context) : PlayerWrapper<PlayerView> {

    /** Set url as a source */
    override fun source(url: String): PlayerWrapper<PlayerView> {
        val appname = context.getString(R.string.app_name)
        val useragent = Util.getUserAgent(context, appname)
        val datasourceFactory = DefaultDataSourceFactory(context, useragent)
        val mediasource = ExtractorMediaSource.Factory(datasourceFactory)
            .createMediaSource(Uri.parse(url))
        player.prepare(mediasource)
        return this
    }

    /** Attach player to a view */
    override fun attachToView(view: PlayerView) = view.setPlayer(player)

    /** Detach player from a view */
    override fun detachFromView(view: PlayerView) = view.setPlayer(null)

    /** Release player */
    override fun release() = player.release()
}