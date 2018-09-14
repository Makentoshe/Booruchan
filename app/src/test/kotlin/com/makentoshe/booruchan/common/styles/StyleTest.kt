package com.makentoshe.booruchan.common.styles

import com.makentoshe.booruchan.R
import junit.framework.Assert.assertEquals
import org.junit.Test

abstract class StyleTest(protected val style: Style) {

    @Test
    open fun `toolbar height in dp`() {
        assertEquals(style.dpToolbarHeight, 56)
    }

    @Test
    fun `test companion object`() {
        assertEquals(R.style.Astarte, Style.Astarte)
        assertEquals(R.style.Shuvi, Style.Shuvi)
        assertEquals(R.style.Rin, Style.Rin)
    }

    @Test
    abstract fun `toolbar background color`()

    @Test
    abstract fun `toolbar text color`()

    @Test
    abstract fun `style id`()

    @Test
    abstract fun `search icon`()

    @Test
    abstract fun `avd from cross to magnify`()

    @Test
    abstract fun `avd from magnify to cross`()

    @Test
    abstract fun `assent color`()

    @Test
    abstract fun `cross icon`()

    @Test
    abstract fun `clear icon`()
}