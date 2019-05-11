package com.makentoshe.booruchan.screen.settings

import android.content.SharedPreferences
import android.util.Log
import com.makentoshe.booruchan.BuildConfig

class AppSettings(sharedPreferences: SharedPreferences) {

    val default = Default(sharedPreferences)

    class Default(private val sharedPreferences: SharedPreferences) {

        var nsfw: Boolean
            set(value) {
                if (BuildConfig.DEBUG) Log.i(this::class.java.name, "nsfw=$value")
                sharedPreferences.edit().putBoolean(this::nsfw.name, value).apply()
            }
            get() {
                return sharedPreferences.getBoolean(this::nsfw.name, false)
            }

        var alert: Boolean
            set(value) {
                if (BuildConfig.DEBUG) Log.i(this::class.java.name, "alert=$value")
                sharedPreferences.edit().putBoolean(this::alert.name, value).apply()
            }
            get() {
                return sharedPreferences.getBoolean(this::alert.name, true)
            }
    }
}