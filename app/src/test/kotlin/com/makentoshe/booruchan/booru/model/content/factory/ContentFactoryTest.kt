package com.makentoshe.booruchan.booru.model.content.factory

import com.makentoshe.booruchan.common.Activity
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.lang.IllegalArgumentException

@RunWith(RobolectricTestRunner::class)
class ContentFactoryTest {

    @Test
    fun `should create posts content factory`() {
        assertNotNull(ContentFactory.createFactory(0, mockk()))
    }

    @Test
    fun `should create users content factory`() {
        assertNotNull(ContentFactory.createFactory(2, mockk()))
    }

    @Test
    fun `should create settings content factory`() {
        assertNotNull(ContentFactory.createFactory(3, mockk()))
    }

    @Test
    fun `should create comments content factory`() {
        assertNotNull(ContentFactory.createFactory(1, mockk()))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should not create gallery factory for undefined params`() {
        assertNotNull(ContentFactory.createFactory(-1, mockk()))
    }

    class ActivityImpl: Activity()

}