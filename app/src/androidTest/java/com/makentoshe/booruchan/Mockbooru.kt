package com.makentoshe.booruchan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.api.*
import io.mockk.every
import io.mockk.mockk
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class Mockbooru(private val context: Context) : Booru {
    override val title: String
        get() = "Mockbooru"

    override fun getCustom(): Custom {
        val bos = ByteArrayOutputStream()
        BitmapFactory.decodeResource(context.resources, R.drawable.test)
            .compress(Bitmap.CompressFormat.PNG, 100, bos)

        return mockk<Custom>().apply {
            every { request(any()) } returns ByteArrayInputStream(bos.toByteArray())
        }
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