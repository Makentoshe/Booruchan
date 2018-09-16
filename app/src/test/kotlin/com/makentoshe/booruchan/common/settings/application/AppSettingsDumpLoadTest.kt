package com.makentoshe.booruchan.common.settings.application

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AppSettingsDumpLoadTest {

    @Test
    fun `test style key`() {
        val obj = object: AppSettingsDumpLoad() {
            val protectedSTYLE_KEY = this.STYLE_KEY
        }
        assertEquals("Style", obj.protectedSTYLE_KEY)
    }

}