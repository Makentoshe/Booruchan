package com.makentoshe.booruchan.screen.settings

import android.content.Context

object AppSettings {

    fun setNSFW(context: Context, boolean: Boolean) {
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).edit()
            .putBoolean(NSFW, boolean)
            .apply()
    }

    fun getNSFW(context: Context): Boolean {
        return context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE).getBoolean(NSFW, false)
    }

    private const val APPLICATION = "AppSettings"
    private const val NSFW = "NotSafeForWatching"
}