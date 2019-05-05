package com.makentoshe.booruchan.screen.settings.model

import com.makentoshe.booruchan.screen.settings.SettingsDefaultScreen
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SettingsScreenBuilderTest {

    private lateinit var builder: SettingsScreenBuilder

    @Before
    fun init() {
        builder = SettingsScreenBuilder()
    }

    @Test
    fun shouldCreateDefaultScreen() {
        val screen = builder.build(0)
        assertEquals(screen::class.java, SettingsDefaultScreen::class.java)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowException() {
        builder.build(1)
    }
}