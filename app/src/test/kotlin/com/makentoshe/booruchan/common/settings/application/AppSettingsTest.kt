package com.makentoshe.booruchan.common.settings.application

import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.styles.AstarteStyle
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
    fun `set Shuvi style`() {
        appSettings.setStyle(Style.Shuvi)
        assertEquals(R.style.Shuvi, appSettings.getStyle().styleId)
    }

    @Test
    fun `set Astarte style`() {
        appSettings.setStyle(Style.Astarte)
        assertEquals(R.style.Astarte, appSettings.getStyle().styleId)
    }

    @Test
    fun `set Rin style`() {
        appSettings.setStyle(Style.Rin)
        assertEquals(R.style.Rin, appSettings.getStyle().styleId)
    }

    @Test
    fun `set another style will cause set upping default style`() {
        appSettings.setStyle(R.style.Base_AlertDialog_AppCompat)
        assertEquals(R.style.Astarte, appSettings.getStyle().styleId)
    }

    @Test
    fun `set Astarte style when another style is set upped`() {
        appSettings.setStyle(Style.Rin)
        assertEquals(R.style.Rin, appSettings.getStyle().styleId)
        appSettings.setStyle(Style.Astarte)
        assertEquals(R.style.Astarte, appSettings.getStyle().styleId)
    }

}