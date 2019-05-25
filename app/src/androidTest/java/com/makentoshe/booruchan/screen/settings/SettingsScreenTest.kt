package com.makentoshe.booruchan.screen.settings

import com.makentoshe.booruchan.screen.SettingsScreen
import com.makentoshe.booruchan.screen.settings.fragment.SettingsFragment
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SettingsScreenTest {

    private lateinit var screen: SettingsScreen

    @Before
    fun init() {
        screen = SettingsScreen()
    }

    @Test
    fun shouldCreateSettingsFragment() {
        assertEquals(SettingsFragment::class.java, screen.fragment::class.java)
    }

}