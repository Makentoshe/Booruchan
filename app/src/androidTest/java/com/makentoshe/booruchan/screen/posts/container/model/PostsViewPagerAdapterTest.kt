package com.makentoshe.booruchan.screen.posts.container.model

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.screen.posts.page.PostsPageFragment
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class PostsViewPagerAdapterTest : KoinTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var adapter: PostsViewPagerAdapter

    @Before
    fun init() {
        activity = rule.launchActivity(null)
        adapter = PostsViewPagerAdapter(activity.supportFragmentManager, setOf(), mockk())
    }

    @Test
    fun shouldContainsDefaultCountIsMaxIntValue() {
        assertEquals(Int.MAX_VALUE, adapter.count)
    }

    @Test
    fun shouldCreatePostPageFragments() {
        val f1 = adapter.getItem(0)
        val f2 = adapter.getItem(0)

        assertEquals(PostsPageFragment::class, f1::class)
        assertEquals(PostsPageFragment::class, f2::class)
        assertNotEquals(f1, f2)
    }

    @Test
    fun shouldCopy() {
        val count = 39
        val copyAdapter = adapter.copy(count)

        assertEquals(count, copyAdapter.count)
    }
}