package com.makentoshe.booruchan.screen.settings

import android.content.Context

object AppSettings {

    fun setNsfw(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(NSFW, boolean)
            .apply()
    }

    fun getNsfw(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(NSFW, false)
    }

    fun getNsfwAlert(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(NSFW_ALERT, true)
    }

    fun setNsfwAlert(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(NSFW_ALERT, boolean).apply()
    }

    fun setStreamingDownload(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(STREAM_DOWNLOAD, boolean).apply()
    }

    fun getStreamingDownload(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(STREAM_DOWNLOAD, true)
    }

    fun setWebmPlayingOnPlace(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(WEBM_PLAYING, boolean).apply()
    }

    fun getWebmPlayingOnPlace(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(WEBM_PLAYING, true)
    }


    private const val APPLICATION = "AppSettings"
    private const val NSFW = "NotSafeForWatching"
    private const val NSFW_ALERT = "NotSafeForWatchingAlert"
    private const val STREAM_DOWNLOAD = "StreamingDownload"
    private const val WEBM_PLAYING = "WebMPlayingOnPlace"
}