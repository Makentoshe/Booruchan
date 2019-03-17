package com.makentoshe.booruchan

import com.makentoshe.booruchan.api.*
import io.mockk.every
import io.mockk.mockk

class Mockbooru : Booru {
    override val title: String
        get() = "Mockbooru"

    override fun getCustom(): Custom {
        TODO("not implemented")
    }

    override fun getAutocomplete(): Autocomplete {
        TODO("not implemented")
    }

    override fun getPosts(): Posts {
        val mock = mockk<Posts>()
        every { mock.request(any()) } returns Array(12) { Post.create(it.toLong()) }.toList()
        return mock
    }

    override fun getTagParser(): Parser<List<Tag>> {
        TODO("not implemented")
    }

    override fun getPostParser(): Parser<List<Post>> {
        TODO("not implemented")
    }
}