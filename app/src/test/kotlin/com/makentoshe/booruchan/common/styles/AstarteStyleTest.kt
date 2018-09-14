package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AstarteStyleTest: StyleTest(AstarteStyle()) {

    override fun `clear icon`() {
        assertEquals(R.drawable.ic_close_vector_black, style.clearIcon)
    }

    override fun `cross icon`() {
        assertEquals(R.drawable.ic_close_vector_white, style.crossIcon)
    }

    override fun `assent color`() {
        assertEquals(R.color.IndigoAssent200, style.assentColor)
    }

    override fun `avd from cross to magnify`() {
        assertEquals(R.drawable.avd_close_magnify_vector_white, style.avdFromCrossToMagnify)
    }

    override fun `avd from magnify to cross`() {
        assertEquals(R.drawable.avd_magnify_close_vector_white, style.avdFromMagnifyToCross)
    }

    override fun `style id`() {
        assertEquals(Style.Astarte, style.styleId)
    }

    override fun `toolbar background color`() {
        assertEquals(R.color.MaterialIndigo500, style.toolbarBackgroundColor)
    }

    override fun `toolbar text color`() {
        assertEquals(android.R.color.white, style.toolbarForegroundColor)
    }

    override fun `search icon`() {
        assertEquals(R.drawable.ic_magnify_vector_white, style.searchIcon)
    }

}