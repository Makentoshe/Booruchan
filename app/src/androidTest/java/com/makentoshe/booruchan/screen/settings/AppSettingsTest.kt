package com.makentoshe.booruchan.screen.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest

class AppSettingsTest : KoinTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var default: AppSettings.Default

    @Before
    fun init() {
        val sp = instrumentation.context.getSharedPreferences(this::class.java.simpleName, Context.MODE_PRIVATE)
        val appSettings = AppSettings(sp)
        default = spyk(appSettings.default)
    }

    @Test
    fun shouldChangeNsfwSetting() {
        default.nsfw = true
        assertEquals(true, default.nsfw)

        default.nsfw = false
        assertEquals(false, default.nsfw)
    }

    @Test
    fun shouldChangeNsfwAlertSetting() {
        default.alert = true
        assertEquals(true, default.alert)

        default.alert = false
        assertEquals(false, default.alert)
    }
}