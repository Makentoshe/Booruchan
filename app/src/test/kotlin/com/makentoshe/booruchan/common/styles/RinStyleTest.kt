package com.makentoshe.booruchan.common.styles

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
        assertEquals(android.R.color.black, style.toolbarForegroundColor)
    }

    override fun `style id`() {
        assertEquals(Style.Rin, style.styleId)
    }

}