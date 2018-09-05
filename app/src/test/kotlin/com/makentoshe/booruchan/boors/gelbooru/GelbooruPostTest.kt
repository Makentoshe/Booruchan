package com.makentoshe.booruchan.boors.gelbooru

import com.makentoshe.booruchan.boors.entity.PostTest
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

class GelbooruPostTest: PostTest() {

    private lateinit var post: Gelbooru.Post

    @Before
    fun setup() {
        val post = Gelbooru.Post()
        post.fill(generateData())
        this.post = post
    }

    @Test
    fun `should contain preview height`() {
        Assert.assertEquals(100, post.previewHeight)
    }

    @Test
    fun `should contain preview width`() {
        Assert.assertEquals(150, post.previewWidth)
    }

    @Test
    fun `should contain sample height`() {
        Assert.assertEquals(900, post.sampleHeight)
    }

    @Test
    fun `should contain sample width`() {
        Assert.assertEquals(646, post.sampleWidth)
    }

    @Test
    fun `should contain height`() {
        Assert.assertEquals(1500, post.height)
    }

    @Test
    fun `should contain width`() {
        Assert.assertEquals(900, post.width)
    }

    @Test
    fun `should contain change data`() {
        Assert.assertEquals("1536165214", post.change)
    }

    @Test
    fun `should contain status`() {
        Assert.assertEquals("active", post.status)
    }

    @Test
    fun `should contain source`() {
        Assert.assertEquals("http://www.someshit.yest/...", post.source)
    }

    @Test
    fun `should contain md5`() {
        Assert.assertEquals("039a35848c87eda66b7cd34d1bcff85e", post.md5)
    }

    @Test
    fun `should contain has_children`() {
        Assert.assertEquals(true, post.hasChildren)
    }

    @Test
    fun `should contain has_notes`() {
        Assert.assertEquals(true, post.hasNotes)
    }

    @Test
    fun `should contain parent id`() {
        Assert.assertEquals(-1, post.parentId)
    }

    override fun generateData(): HashMap<String, String> {
        val map = super.generateData()
        map["preview_height"] = "100"
        map["preview_width"] = "150"
        map["sample_height"] = "900"
        map["sample_width"] = "646"
        map["has_notes"] = "true"
        map["has_children"] = "true"
        map["height"] = "1500"
        map["width"] = "900"
        map["change"] = "1536165214"
        map["status"] = "active"
        map["source"] = "http://www.someshit.yest/..."
        map["md5"] = "039a35848c87eda66b7cd34d1bcff85e"
        map["parent_id"] = ""
        return map
    }
}