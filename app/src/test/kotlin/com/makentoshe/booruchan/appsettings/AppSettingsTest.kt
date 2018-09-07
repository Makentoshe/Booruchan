package com.makentoshe.booruchan.appsettings

import com.makentoshe.booruchan.styles.AstarteStyle
import com.makentoshe.booruchan.styles.Style
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AppSettingsTest {

    private val appSettings = AppSettings()

    @Test
    fun `default app settings style values`() {
        assertEquals(Style.Astarte, appSettings.getStyleVal())
        assertTrue(appSettings.getStyle() is AstarteStyle)
    }

}