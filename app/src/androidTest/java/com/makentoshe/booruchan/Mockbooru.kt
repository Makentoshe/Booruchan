package com.makentoshe.booruchan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.api.*
import com.makentoshe.booruchan.api.Parser
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.network.HttpResult
import io.mockk.every
import io.mockk.mockk
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class Mockbooru(private val context: Context) : Booru {
    override val title: String
        get() = "Mockbooru"

    override fun getCustom(params: Map<String, String>): Custom {
        val bos = ByteArrayOutputStream()
        BitmapFactory.decodeResource(context.resources, R.drawable.test)
            .compress(Bitmap.CompressFormat.PNG, 100, bos)

        return mockk<Custom>().apply {
            val result = mockk<HttpResult>()
            every { result.stream } returns ByteArrayInputStream(bos.toByteArray())
            every { request(any()) } returns result
        }
    }

    override fun headCustom(params: Map<String, String>): Custom {
        TODO("Not need in tests yet")
    }

    override fun getAutocomplete(): Autocomplete {
        val mock = mockk<Autocomplete>()
        lateinit var str: String
        every {
            str = any()
            mock.request(str)
        } returns Array(10) { Tag.create(str) }.toList()
        return mock
    }

    override fun getPosts(): Posts {
        val mock = mockk<Posts>()
        every { mock.request(any()) } returns Array(12) { Post.create(it.toLong()) }.toList()
        return mock
    }

    override fun getAutocompleteTagParser(): Parser<List<Tag>> {
        TODO("not implemented")
    }

    override fun getPostParser(): Parser<List<Post>> {
        TODO("not implemented")
    }
}