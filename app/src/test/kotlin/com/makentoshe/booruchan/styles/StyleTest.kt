package com.makentoshe.booruchan.styles

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
    }

}