package com.makentoshe.booruchan.start.model

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ServicesGeneratorTest {

    private lateinit var servicesGenerator: ServicesGenerator

    @Before
    fun init() {
        servicesGenerator = ServicesGenerator(RuntimeEnvironment.systemContext)
    }

    @Test
    fun `generate services list`() {
        val list = servicesGenerator.generateList()
        assertEquals(1, list.size)
        assertTrue(list.contains(Gelbooru::class.java.simpleName))
    }

    @Test
    fun `generate class list`() {
        val list = servicesGenerator.generateClassList()
        assertEquals(1, list.size)
        assertTrue(list.contains(Gelbooru::class.java as Class<Boor>))
    }

    @Test
    fun `adapter for services list`() {
        val list = listOf("Gelbooru", "Safebooru", "Testbooru")
        val adapter = servicesGenerator.createAdapter(list)
        assertEquals(3, adapter.count)
    }

}