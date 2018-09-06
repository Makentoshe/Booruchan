package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AstarteStyleTest: StyleTest(AstarteStyle()) {

    @Test
    fun `toolbar background color`() {
        assertEquals(R.color.colorPrimary, style.toolbarBackgroundColor)
    }

    @Test
    fun `toolbar text color`() {
        assertEquals(android.R.color.white, style.toolbarTextColor)
    }

}