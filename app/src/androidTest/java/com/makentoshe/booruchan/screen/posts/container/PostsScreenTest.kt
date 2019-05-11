package com.makentoshe.booruchan.screen.posts.container

import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostsScreenTest {

    private lateinit var screen: PostsScreen

    @Before
    fun init() {
        screen = PostsScreen(mockk(), setOf())
    }

    @Test
    fun shouldCreateMultipleFragments() {
        val f1 = screen.fragment
        val f2 = screen.fragment

        assertEquals(PostsFragment::class, f1::class)
        assertEquals(PostsFragment::class, f2::class)
        assertNotEquals(f1, f2)
    }
}