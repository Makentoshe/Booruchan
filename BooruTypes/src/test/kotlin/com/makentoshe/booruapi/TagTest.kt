package com.makentoshe.booruapi

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TagTest {

    private lateinit var map: HashMap<String, String>

    @Before
    fun init() {
        map = HashMap()
    }

    @Test
    fun `should create tag with value "id" is 2`() {
        map["id"] = 2.toString()
        val tag = Tag(map)
        assertEquals(2, tag.id)
    }

    @Test
    fun `should create tag with value "name" is "test"`() {
        map["name"] = "test"
        val tag = Tag(map)
        assertEquals("test", tag.name)
    }

    @Test
    fun `should create tag with value "tag" is "test"`() {
        map["tag"] = "test"
        val tag = Tag(map)
        assertEquals("test", tag.name)
    }

    @Test
    fun `should create tag with value "count" is 2`() {
        map["count"] = 2.toString()
        val tag = Tag(map)
        assertEquals(2, tag.count)
    }

    @Test
    fun `should create tag with value "type" is 0`() {
        map["type"] = 0.toString()
        val tag = Tag(map)
        assertEquals(Tag.Type.GENERAL, tag.type)
    }

    @Test
    fun `should create tag with value "type" is 1`() {
        map["type"] = 1.toString()
        val tag = Tag(map)
        assertEquals(Tag.Type.ARTIST, tag.type)
    }

    @Test
    fun `should create tag with value "type" is 3`() {
        map["type"] = 3.toString()
        val tag = Tag(map)
        assertEquals(Tag.Type.COPYRIGHT, tag.type)
    }

    @Test
    fun `should create tag with value "type" is 4`() {
        map["type"] = 4.toString()
        val tag = Tag(map)
        assertEquals(Tag.Type.CHARACTER, tag.type)
    }

    @Test
    fun `should create tag with value "type" is 5`() {
        map["type"] = 5.toString()
        val tag = Tag(map)
        assertEquals(Tag.Type.METADATA, tag.type)
    }

    @Test
    fun `should create tag with value "type" is 6`() {
        map["type"] = 6.toString()
        val tag = Tag(map)
        assertEquals(Tag.Type.UNDEFINED, tag.type)
    }

    @Test
    fun `should create tag with value "type" is CHARACTER`() {
        map["type"] = Tag.Type.CHARACTER.toString().toLowerCase()
        val tag = Tag(map)
        assertEquals(Tag.Type.CHARACTER, tag.type)
    }

    @Test
    fun `should create tag with value "type" is GENERAL`() {
        map["type"] = Tag.Type.GENERAL.toString().toLowerCase()
        val tag = Tag(map)
        assertEquals(Tag.Type.GENERAL, tag.type)
    }

    @Test
    fun `should create tag with value "type" is COPYRIGHT`() {
        map["type"] = Tag.Type.COPYRIGHT.toString().toLowerCase()
        val tag = Tag(map)
        assertEquals(Tag.Type.COPYRIGHT, tag.type)
    }

    @Test
    fun `should create tag with value "type" is METADATA`() {
        map["type"] = Tag.Type.METADATA.toString().toLowerCase()
        val tag = Tag(map)
        assertEquals(Tag.Type.METADATA, tag.type)
    }

    @Test
    fun `should create tag with value "type" is ARTIST`() {
        map["type"] = Tag.Type.ARTIST.toString().toLowerCase()
        val tag = Tag(map)
        assertEquals(Tag.Type.ARTIST, tag.type)
    }

    @Test
    fun `should create tag with undefined value "type"`() {
        map["type"] = "sas"
        val tag = Tag(map)
        assertEquals(Tag.Type.UNDEFINED, tag.type)
    }

    @Test
    fun `should create tag with value "ambiguous" is 0`() {
        map["ambiguous"] = 0.toString()
        val tag = Tag(map)
        assertEquals(false, tag.ambiguous)
    }

    @Test
    fun `should create tag with value "ambiguous" is 1`() {
        map["ambiguous"] = 1.toString()
        val tag = Tag(map)
        assertEquals(true, tag.ambiguous)
    }

    @Test
    fun `should create tag with value "ambiguous" is false`() {
        map["ambiguous"] = false.toString()
        val tag = Tag(map)
        assertEquals(false, tag.ambiguous)
    }

    @Test
    fun `should create tag with value "ambiguous" is true`() {
        map["ambiguous"] = true.toString()
        val tag = Tag(map)
        assertEquals(true, tag.ambiguous)
    }

    @Test
    fun `should create tag with constructor params`() {
        val name = "test"
        val id = 0
        val count = 1
        val ambiguous = false
        val type = Tag.Type.GENERAL
        val tag = Tag(name, id, count, ambiguous, type)
        assertEquals(name, tag.name)
        assertEquals(id, tag.id)
        assertEquals(count, tag.count)
        assertEquals(ambiguous, tag.ambiguous)
        assertEquals(type, tag.type)
    }
}