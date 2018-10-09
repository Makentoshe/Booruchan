package com.makentoshe.booruchan.booru.content.posts.model

import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model.AdapterDataLoaderBuilder
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AdapterDataLoaderBuilderTest {

    @Test
    fun sas() {
        val adapterDataLoaderBuilder = AdapterDataLoaderBuilder(mockk(), mockk())
        val adapterDataLoader = adapterDataLoaderBuilder.build("")
        assertNotNull(adapterDataLoader)
        assertEquals("", adapterDataLoader.searchTerm)
    }

}