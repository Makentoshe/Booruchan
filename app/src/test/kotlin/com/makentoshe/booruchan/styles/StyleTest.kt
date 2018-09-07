package com.makentoshe.booruchan.styles

import junit.framework.Assert.assertEquals
import org.junit.Test

abstract class StyleTest(protected val style: Style) {

    @Test
    open fun `toolbar height in dp`() {
        assertEquals(style.dpToolbarHeight, 56)
    }

    @Test
    fun `test companion object`() {
        assertEquals(0, Style.Astarte)
    }

}