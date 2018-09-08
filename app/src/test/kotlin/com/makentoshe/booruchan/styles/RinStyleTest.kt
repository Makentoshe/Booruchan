package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RinStyleTest : StyleTest(RinStyle()) {

    override fun `toolbar background color`() {
        assertEquals(R.color.MaterialYellow500, style.toolbarBackgroundColor)
    }

    override fun `toolbar text color`() {
        assertEquals(android.R.color.white, style.toolbarTextColor)
    }

    override fun `style id`() {
        assertEquals(Style.Rin, style.styleId)
    }

    override fun `style name`() {
        assertEquals(Style.RinName, style.styleName)
    }
}