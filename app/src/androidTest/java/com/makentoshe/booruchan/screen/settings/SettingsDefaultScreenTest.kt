package com.makentoshe.booruchan.screen.settings

import com.makentoshe.booruchan.screen.settings.fragment.SettingsDefaultFragment
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingsDefaultScreenTest {

    private lateinit var screen: SettingsDefaultScreen

    @Before
    fun init() {
        screen = SettingsDefaultScreen(0)
    }

    @Test
    fun shouldContainTitle() {
        assertEquals("Default", screen.screenKey)
    }

    @Test
    fun shouldCreateFragment() {
        assertEquals(SettingsDefaultFragment::class.java, screen.fragment::class.java)
    }
}