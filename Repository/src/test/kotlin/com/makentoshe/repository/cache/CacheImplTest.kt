package com.makentoshe.repository.cache

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CacheImplTest {

    private lateinit var cache: Cache<String, Int>

    @Before
    fun init() {
        cache = CacheImpl(2)
    }

    @Test
    fun `should get value if present`() {
        cache.get("key") { 1 }
        assertEquals(1, cache.getIfPresent("key"))
    }

    @Test
    fun `should remove value`() {
        cache.get("key") { 1 }
        cache.remove("key")
        assertNull(cache.getIfPresent("key"))
    }

    @Test
    fun `should return all values`() {
        cache.get("a") { 1 }
        cache.get("b") { 2 }
        assertEquals(2, cache.getAll().size)
    }

    @Test
    fun `should replace old value by new`() {
        cache.get("1") { 1 }
        cache.get("2") { 2 }
        cache.get("3") { 3 }
        assertEquals(2, cache.getAll().size)
        assertEquals(3, cache.getIfPresent("3"))
        assertEquals(2, cache.getIfPresent("2"))
        assertNull(cache.getIfPresent("1"))
    }
}