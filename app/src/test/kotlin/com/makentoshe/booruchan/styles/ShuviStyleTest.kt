package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ShuviStyleTest: StyleTest(ShuviStyle()) {

    @Test
    fun `toolbar background color`() {
        Assert.assertEquals(R.color.MaterialPurple500, style.toolbarBackgroundColor)
    }

    @Test
    fun `toolbar text color`() {
        Assert.assertEquals(android.R.color.white, style.toolbarTextColor)
    }

}