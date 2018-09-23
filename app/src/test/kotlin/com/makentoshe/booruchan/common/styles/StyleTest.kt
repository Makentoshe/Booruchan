package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

abstract class StyleTest(protected val style: Style) {

    @Test
    open fun `toolbar height in dp`() {
        assertEquals(style.dpToolbarHeight, 56)
    }

    @Test
    fun `test companion object`() {
        assertEquals(R.style.Astarte, Style.Astarte)
        assertEquals(R.style.Shuvi, Style.Shuvi)
        assertEquals(R.style.Rin, Style.Rin)
    }

    @Test
    fun `array of style names should contain style names`() {
        assertTrue(Style.arrayOfStyleNames.contains(Style.AstarteName))
        assertTrue(Style.arrayOfStyleNames.contains(Style.ShuviName))
        assertTrue(Style.arrayOfStyleNames.contains(Style.RinName))
    }

    @Test
    fun `avd from cross to magnify`() {
        assertEquals(R.drawable.avd_close_magnify_vector_white, style.avdFromCrossToMagnify)
    }
    @Test
    fun `avd from magnify to cross`() {
        assertEquals(R.drawable.avd_magnify_close_vector_white, style.avdFromMagnifyToCross)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should return correct style index`() {
        assertEquals(0, Style.getStyleIndex(Style.Astarte))
        assertEquals(1, Style.getStyleIndex(Style.Shuvi))
        assertEquals(2, Style.getStyleIndex(Style.Rin))
        Style.getStyleIndex(R.style.Base_AlertDialog_AppCompat)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should return correct style id by name`() {
        assertEquals(AstarteStyle().styleId, Style.getStyleByName(Style.AstarteName).styleId)
        assertEquals(ShuviStyle().styleId, Style.getStyleByName(Style.ShuviName).styleId)
        assertEquals(RinStyle().styleId, Style.getStyleByName(Style.RinName).styleId)
        Style.getStyleByName("Any style name that is not correct for this method")
    }

    @Test
    abstract fun `toolbar background color`()

    @Test
    abstract fun `toolbar text color`()

    @Test
    abstract fun `style id`()

    @Test
    abstract fun `secondary assent`()

}