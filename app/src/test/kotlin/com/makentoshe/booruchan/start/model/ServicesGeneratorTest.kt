package com.makentoshe.booruchan.start.model

import android.R
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.makentoshe.booruchan.boors.gelbooru.Gelbooru
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
    fun `generate list `() {
        val list = servicesGenerator.generateList()
        assertEquals(1, list.size)
        assertTrue(list.contains(Gelbooru::class.java.simpleName))
    }

    @Test
    fun `adapter for services list`() {
        val list = listOf("Gelbooru", "Safebooru", "Testbooru")
        val adapter = servicesGenerator.createAdapter(list)
        assertEquals(3, adapter.count)
    }

}