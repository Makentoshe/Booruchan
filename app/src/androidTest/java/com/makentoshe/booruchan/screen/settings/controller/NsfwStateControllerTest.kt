package com.makentoshe.booruchan.screen.settings.controller

import androidx.test.platform.app.InstrumentationRegistry
import com.makentoshe.booruchan.screen.settings.AppSettings
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class NsfwStateControllerTest : KoinTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val identifier = this::class.java.simpleName

    private lateinit var controller: NsfwStateController
    private lateinit var appSettings: AppSettings

    @Before
    fun init() {
        stopKoin()
        startKoin {
            androidContext(instrumentation.context)
            modules(module {
                single { AppSettings(identifier) }
                single { NsfwStateController() }
            })
        }

        controller = get()
        appSettings = get()
    }

    @Test
    fun shouldEnableNsfw() {
        controller.enable()

        assertEquals(true, controller.state)
        assertEquals(true, appSettings.default.nsfw)
    }

    @Test
    fun shouldDisableNsfw() {
        appSettings.default.nsfw = true

        controller.disable()

        assertEquals(false, controller.state)
        assertEquals(false, appSettings.default.nsfw)
    }

    @Test
    fun shouldDisableAlert() {
        controller.disableAlert()

        assertEquals(false, controller.shouldShowAlert)
        assertEquals(false, appSettings.default.alert)
    }

    @After
    fun after() {
        //return to initial state
        appSettings.default.nsfw = false
        appSettings.default.alert = true
    }
}