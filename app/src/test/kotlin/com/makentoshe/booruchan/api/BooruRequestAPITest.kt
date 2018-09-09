package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.BoorRequestAPI
import junit.framework.Assert.assertEquals
import org.junit.Test

abstract class BooruRequestAPITest(private val api: BoorRequestAPI) {

    @Test
    fun getCustomRequest() {
        val term = "CustomRequestString"
        val url = api.getCustomRequest(term)
        val expected = getCustomRequestExpected(term)
        assertEquals(expected, url)
    }

    @Test
    fun getPostByIdRequest() {
        val id = 4391534
        val request = api.getPostByIdRequest(id)
        val expected = getPostByIdRequestExpected(id)
        assertEquals(expected, request)
    }

    @Test
    fun getPostsByTagsRequest() {
        val limit = 30
        val tags = "hatsune_miku"
        val page = 3
        val url = api.getPostsByTagsRequest(limit, tags, page)
        val expected = getPostsByTagsRequestExpected(limit, tags, page)
        assertEquals(expected, url)
    }

    @Test
    fun getPostViewByIdRequest() {
        val id = 4391534
        val url = api.getPostViewByIdRequest(id)
        val expected = getPostViewByIdRequestExpected(id)
        assertEquals(expected, url)
    }

    @Test
    fun getAutocompleteSearchRequest() {
        val term = "hats"
        val url = api.getAutocompleteSearchRequest(term)
        val expected = getAutocompleteSearchRequestExpected(term)
        assertEquals(expected, url)
    }

    @Test
    fun getUserProfileViewByIdRequest() {
        val stringAsIdOrName = "393939"
        val url = api.getUserProfileViewByIdRequest(stringAsIdOrName)
        val expected = getUserProfileViewByIdRequestExpected(stringAsIdOrName)
        assertEquals(expected, url)
    }

    @Test
    fun getCommentByPostIdRequest() {
        val postID = 393939
        val url = api.getCommentByPostIdRequest(postID)
        val expected = getCommentByPostIdRequestExpected(postID)
        assertEquals(expected, url)
    }

    abstract fun getPostByIdRequestExpected(id: Int): String

    abstract fun getPostsByTagsRequestExpected(limit: Int, tags: String, page: Int): String

    abstract fun getPostViewByIdRequestExpected(id: Int): String

    abstract fun getAutocompleteSearchRequestExpected(term: String): String

    abstract fun getUserProfileViewByIdRequestExpected(id: String): String

    abstract fun getCommentByPostIdRequestExpected(id: Int): String

    abstract fun getCustomRequestExpected(str: String): String

}