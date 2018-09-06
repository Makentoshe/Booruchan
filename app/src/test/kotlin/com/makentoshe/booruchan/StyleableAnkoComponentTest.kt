package com.makentoshe.booruchan

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.makentoshe.booruchan.styles.Style
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import org.jetbrains.anko.*
import org.junit.Test


class StyleableAnkoComponentTest {

    @Test
    fun `test constructor`() {
        val style = mockk<Style>()
        object : StyleableAnkoComponent<AppCompatActivity>(style){
            override fun createView(ui: AnkoContext<AppCompatActivity>): View {
                assertNotNull(style)
                return with(ui) { verticalLayout {} }
            }
        }

    }

}