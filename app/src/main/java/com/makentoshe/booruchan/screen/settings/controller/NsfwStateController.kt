package com.makentoshe.booruchan.screen.settings.controller

import android.content.Context
import com.makentoshe.booruchan.screen.settings.AppSettings

/**
 * Controls nsfw setting on the application settings level.
 */
class NsfwStateController(private val appSettings: AppSettings, private val context: Context) {

    /**
     * Enable nsfw setting.
     */
    fun enable() = appSettings.setNsfw(context, true)

    /**
     * Disable nsfw setting.
     */
    fun disable() = appSettings.setNsfw(context, false)

    /**
     * Disable alert nsfw setting. After this method was called,
     * the alert dialog will not be appears when the nsfw setting enables.
     */
    fun disableAlert() = appSettings.setNsfwAlert(context, false)

    /**
     * Returns the current state of the nsfw setting
     */
    val state: Boolean
        get() = appSettings.getNsfw(context)

    /**
     * Returns the current state of the nsfw alert setting
     */
    val shouldShowAlert: Boolean
        get() = appSettings.getNsfwAlert(context)
}