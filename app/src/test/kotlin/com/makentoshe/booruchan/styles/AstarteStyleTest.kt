package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AstarteStyleTest: StyleTest(AstarteStyle()) {

    override fun `style id`() {
        assertEquals(Style.Astarte, style.styleId)
    }

    override fun `style name`() {
        assertEquals(Style.AstarteName, style.styleName)
    }

    override fun `toolbar background color`() {
        assertEquals(R.color.MaterialIndigo500, style.toolbarBackgroundColor)
    }

    override fun `toolbar text color`() {
        assertEquals(android.R.color.white, style.toolbarTextColor)
    }

}