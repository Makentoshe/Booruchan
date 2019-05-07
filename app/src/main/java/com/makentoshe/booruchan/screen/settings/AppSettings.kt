package com.makentoshe.booruchan.screen.settings

import android.content.Context
import org.koin.core.KoinComponent
import org.koin.core.get

class AppSettings(private val identifier: String) {

    val default: Default
        get() = Default(identifier)

    class Default(private val identifier: String) : KoinComponent {

        private val context: Context
            get() = get()

        var nsfw: Boolean
            set(value) {
                context.getSharedPreferences(identifier, Context.MODE_PRIVATE)
                    .edit().putBoolean(this::nsfw.name, value).apply()
            }
            get() {
                return context.getSharedPreferences(identifier, Context.MODE_PRIVATE)
                    .getBoolean(this::nsfw.name, false)
            }

        var alert: Boolean
            set(value) {
                context.getSharedPreferences(identifier, Context.MODE_PRIVATE)
                    .edit().putBoolean(this::alert.name, value).apply()
            }
            get() {
                return context.getSharedPreferences(identifier, Context.MODE_PRIVATE)
                    .getBoolean(this::alert.name, true)
            }
    }
}