package com.makentoshe.booruchan.screen.settings.controller

import com.makentoshe.booruchan.screen.settings.AppSettings

/**
 * Controls nsfw setting on the application settings level.
 */
class NsfwStateController(appSettings: AppSettings) {

    private val default = appSettings.default

    /**
     * Enable nsfw setting.
     */
    fun enable() {
        default.nsfw = true
    }

    /**
     * Disable nsfw setting.
     */
    fun disable() {
        default.nsfw = false
    }

    /**
     * Disable alert nsfw setting. After this method was called,
     * the alert dialog will not be appears when the nsfw setting enables.
     */
    fun disableAlert() {
        default.alert = false
    }

    /**
     * Returns the current state of the nsfw setting
     */
    val state: Boolean
        get() = default.nsfw

    /**
     * Returns the current state of the nsfw alert setting
     */
    val shouldShowAlert: Boolean
        get() = default.alert
}