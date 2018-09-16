package com.makentoshe.booruchan.start.model

import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ServiceListGeneratorTest {

    private val serviceListGenerator = ServiceListGenerator()
    private lateinit var activity: AppCompatActivity

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
    }

    @Test
    fun `test adapter creation with list of services`() {
        val list = listOf("SAS", "ASA", "SSS", "AAA")
        val adapter = serviceListGenerator.createAdapter(activity, list)
        assertEquals(list.size, adapter.count)
        assertEquals(list[0], adapter.getItem(0))
    }

    @Test
    fun `test services list`() {
        val list = serviceListGenerator.servicesList
        assertTrue(list.contains(Gelbooru::class.java.simpleName))
    }

}