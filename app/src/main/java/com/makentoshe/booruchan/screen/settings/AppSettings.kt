package com.makentoshe.booruchan.screen.settings

import android.content.Context
import org.koin.core.KoinComponent
import org.koin.core.get

class AppSettings {

    val default: Default
        get() = Default()

    class Default : KoinComponent {

        private val context: Context
            get() = get()

        var nsfw: Boolean
            set(value) {
                context.getSharedPreferences(AppName, Context.MODE_PRIVATE)
                    .edit().putBoolean(this::nsfw.name, value).apply()
            }
            get() {
                return context.getSharedPreferences(AppName, Context.MODE_PRIVATE)
                    .getBoolean(this::nsfw.name, false)
            }

        var alert: Boolean
            set(value) {
                context.getSharedPreferences(AppName, Context.MODE_PRIVATE)
                    .edit().putBoolean(this::alert.name, value).apply()
            }
            get() {
                return context.getSharedPreferences(AppName, Context.MODE_PRIVATE)
                    .getBoolean(this::alert.name, true)
            }
    }

    companion object {
        private const val AppName = "Booruchan"
    }
}