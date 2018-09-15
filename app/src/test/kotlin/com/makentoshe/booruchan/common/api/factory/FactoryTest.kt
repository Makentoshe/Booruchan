package com.makentoshe.booruchan.common.api.factory

import com.makentoshe.booruchan.common.api.Boor
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FactoryTest {

    @Test(expected = IllegalArgumentException::class)
    fun `factory creation by name should fail`() {
        Factory.createFactory("Some booru service name")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `factory creation by class should fail`() {
        val mock = mockk<Boor>()
        Factory.createFactory(mock.javaClass)
    }

}