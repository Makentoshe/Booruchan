package com.makentoshe.booruchan.screen.start.model

import android.content.Context
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.screen.settings.AppSettings

/**
 * Performs filtering and returns a booru list
 */
class BooruContentFilter(
    private val appSettings: AppSettings,
    private val context: Context,
    private val booruList: List<Class<out Booru>>
) {
    fun getBooruList(): List<Class<out Booru>> {
        return if (appSettings.getNsfw(context)) {
            booruList
        } else {
            booruList.filter { it == Safebooru::class.java }
        }
    }
}