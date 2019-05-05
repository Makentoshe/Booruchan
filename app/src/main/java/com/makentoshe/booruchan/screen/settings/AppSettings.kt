package com.makentoshe.booruchan.screen.settings

import android.content.Context

object AppSettings {

    fun setNsfw(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(NSFW, boolean)
            .apply()
    }

    fun getNsfw(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(
            NSFW, false
        )
    }

    fun getNsfwAlert(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(
            NSFW_ALERT, true
        )
    }

    fun setNsfwAlert(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(NSFW_ALERT, boolean).apply()
    }


    private const val APPLICATION = "AppSettings"
    private const val NSFW = "NotSafeForWatching"
    private const val NSFW_ALERT = "NotSafeForWatchingAlert"
}