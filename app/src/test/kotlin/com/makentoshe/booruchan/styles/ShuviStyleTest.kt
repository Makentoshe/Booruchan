package com.makentoshe.booruchan.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ShuviStyleTest: StyleTest(ShuviStyle()) {

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

}