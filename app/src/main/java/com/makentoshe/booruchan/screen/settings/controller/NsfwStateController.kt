package com.makentoshe.booruchan.screen.settings.controller

import com.makentoshe.booruchan.screen.settings.AppSettings

/**
 * Controls nsfw setting on the application settings level.
 */
class NsfwStateController(private val appSettings: AppSettings) {

    /**
     * Enable nsfw setting.
     */
    fun enable() {
        appSettings.default.nsfw = true
    }

    /**
     * Disable nsfw setting.
     */
    fun disable() {
        appSettings.default.nsfw = false
    }

    /**
     * Disable alert nsfw setting. After this method was called,
     * the alert dialog will not be appears when the nsfw setting enables.
     */
    fun disableAlert() {
        appSettings.default.alert = false
    }

    /**
     * Returns the current state of the nsfw setting
     */
    val state: Boolean
        get() = appSettings.default.nsfw

    /**
     * Returns the current state of the nsfw alert setting
     */
    val shouldShowAlert: Boolean
        get() = appSettings.default.alert
}