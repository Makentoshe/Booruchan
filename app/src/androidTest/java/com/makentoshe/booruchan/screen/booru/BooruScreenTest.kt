package com.makentoshe.booruchan.screen.booru

import com.makentoshe.booruchan.api.Booru
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BooruScreenTest {

    private lateinit var screen: BooruScreen

    private val booru = mockk<Booru>().apply {
        every { title } returns "Testbooru"
        every { nsfw } returns false
    }

    @Before
    fun init() {
        screen = BooruScreen(booru, setOf())
    }

    @Test
    fun shouldCreateFragment() {
        val fragment = screen.fragment
        assertEquals(BooruFragment::class, fragment::class)
    }
}