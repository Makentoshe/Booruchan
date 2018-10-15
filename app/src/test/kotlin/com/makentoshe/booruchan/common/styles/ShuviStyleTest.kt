package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ShuviStyleTest: StyleTest(ShuviStyle()) {

    override fun `secondary assent`() {
        assertEquals(R.color.MaterialPurple200, style.assentSecondaryColor)
    }

    override fun `style id`() {
        Assert.assertEquals(Style.Shuvi, style.styleId)
    }

    override fun `toolbar background color`() {
        Assert.assertEquals(R.color.MaterialPurple500, style.toolbar.primaryColorRes)
    }

    @Test
    override fun `toolbar text color`() {
        Assert.assertEquals(android.R.color.white, style.toolbar.onPrimaryColorRes)
    }

}