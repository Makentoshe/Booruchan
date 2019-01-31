package com.makentoshe.booruchan.postsamples.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ConfirmFileDownloadControllerTest {
    private lateinit var controller: ConfirmFileDownloadController

    @Before
    fun init() {
        controller = ConfirmFileDownloadController()
    }

    @Test
    fun `should remove all subscribers on clear`() {
        var flag = ""
        controller.subscribe { flag = it }
        controller.action("SAS")
        assertEquals("SAS", flag)
        controller.clear()
        controller.action("ASA")
        assertNotEquals("ASA", flag)
    }
}