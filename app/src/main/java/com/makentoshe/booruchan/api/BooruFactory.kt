package com.makentoshe.booruchan.api

import android.content.Context
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.network.HostHttpClient
import com.makentoshe.booruchan.network.HttpClient

class BooruFactory(private val httpClient: HttpClient) {

    fun buildBooru(`class`: Class<out Booru>, context: Context): Booru {
        return when (`class`) {
            Gelbooru::class.java -> buildGelbooru(context)
            else -> throw IllegalArgumentException()
        }
    }

    private fun buildGelbooru(context: Context): Booru {
        val client = HostHttpClient(httpClient, listOf("https://www.gelbooru.com"))
        //todo(define the mirrors or get them from the storage using context)
        return Gelbooru(client)
    }
}