package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ShuviStyleTest: StyleTest(ShuviStyle()) {
    override fun `clear icon`() {
        assertEquals(R.drawable.ic_close_vector_black, style.clearIcon)
    }

    override fun `cross icon`() {
        assertEquals(R.drawable.ic_close_vector_white, style.crossIcon)
    }

    override fun `assent color`() {
        assertEquals(R.color.PurpleAssent200, style.assentColor)
    }

    override fun `avd from cross to magnify`() {
        assertEquals(R.drawable.avd_close_magnify_vector_white, style.avdFromCrossToMagnify)
    }

    override fun `avd from magnify to cross`() {
        assertEquals(R.drawable.avd_magnify_close_vector_white, style.avdFromMagnifyToCross)
    }

    override fun `style id`() {
        Assert.assertEquals(Style.Shuvi, style.styleId)
    }

    override fun `style name`() {
        Assert.assertEquals(Style.ShuviName, style.styleName)
    }

    override fun `toolbar background color`() {
        Assert.assertEquals(R.color.MaterialPurple500, style.toolbarBackgroundColor)
    }

    @Test
    override fun `toolbar text color`() {
        Assert.assertEquals(android.R.color.white, style.toolbarForegroundColor)
    }

    override fun `search icon`() {
        Assert.assertEquals(R.drawable.ic_magnify_vector_white, style.searchIcon)
    }

}