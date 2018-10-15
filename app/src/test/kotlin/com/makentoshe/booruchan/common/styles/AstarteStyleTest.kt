package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AstarteStyleTest: StyleTest(AstarteStyle()) {

    override fun `secondary assent`() {
        assertEquals(R.color.MaterialIndigo200, style.assentSecondaryColor)
    }

    override fun `style id`() {
        assertEquals(Style.Astarte, style.styleId)
    }

    override fun `toolbar background color`() {
        assertEquals(R.color.MaterialIndigo500, style.toolbar.primaryColorRes)
    }

    override fun `toolbar text color`() {
        assertEquals(android.R.color.white, style.toolbar.onPrimaryColorRes)
    }


}