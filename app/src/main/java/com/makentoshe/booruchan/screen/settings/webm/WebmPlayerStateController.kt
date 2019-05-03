package com.makentoshe.booruchan.screen.settings.webm

import android.content.Context
import com.makentoshe.booruchan.screen.settings.AppSettings

class WebmPlayerStateController(
    private val appSettings: AppSettings,
    private val context: Context
) {
    val state: Boolean
        get() = appSettings.getWebmPlayingOnPlace(context)

    fun enable() = appSettings.setWebmPlayingOnPlace(context, true)

    fun disable() = appSettings.setWebmPlayingOnPlace(context, false)
}