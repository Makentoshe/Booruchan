package com.makentoshe.booruchan.appsettings

import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.styles.AstarteStyle
import com.makentoshe.booruchan.common.styles.RinStyle
import com.makentoshe.booruchan.common.styles.ShuviStyle
import com.makentoshe.booruchan.common.styles.Style
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

    @Test
    fun `change style`() {
        appSettings.setStyle(Style.Shuvi)
        assertEquals(Style.Shuvi, appSettings.getStyleVal())
        assertTrue(appSettings.getStyle() is ShuviStyle)

        appSettings.setStyle(Style.Astarte)
        assertEquals(Style.Astarte, appSettings.getStyleVal())
        assertTrue(appSettings.getStyle() is AstarteStyle)

        appSettings.setStyle(Style.Rin)
        assertEquals(Style.Rin, appSettings.getStyleVal())
        assertTrue(appSettings.getStyle() is RinStyle)
    }

}