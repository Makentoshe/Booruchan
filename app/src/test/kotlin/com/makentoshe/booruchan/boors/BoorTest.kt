package com.makentoshe.booruchan.boors

import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BoorTest {

    private lateinit var instance: Boor
    private val name = "Testbooru"
    @Before
    fun init() {
        val requestAPI = mockk<BoorRequestAPI>()
        instance = object: Boor(requestAPI) {
            override fun getBooruName(): String {
                return name
            }

            override fun convertLocalTimeToDefault(time: String): String {
                return time
            }
        }
    }

    @Test
    fun `get booru name`() {
        assertEquals(name, instance.getBooruName())
    }

    @Test
    fun `api should not be null`() {
        assertNotNull(instance.getApi())
    }

    @Test
    fun `should converting time`() {
        val time = "time"
        assertNotNull(time, instance.convertLocalTimeToDefault(time))
    }

}