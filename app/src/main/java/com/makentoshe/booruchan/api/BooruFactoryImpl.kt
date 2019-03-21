package com.makentoshe.booruchan.api

import android.content.Context
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.decorator.HostHttpClient

class BooruFactoryImpl(private val defaultClient: HttpClient) : BooruFactory {

    override fun buildBooru(`class`: Class<out Booru>, context: Context): Booru {
        return when (`class`) {
            Gelbooru::class.java -> buildGelbooru(context)
            Safebooru::class.java -> buildSafebooru(context)
            else -> throw IllegalArgumentException()
        }
    }

    private fun buildGelbooru(context: Context): Booru {
        val hostList = mutableListOf("https://www.gelbooru.com")
        val host = HostHttpClient(defaultClient, hostList)
        return Gelbooru(host)
    }

    private fun buildSafebooru(context: Context): Booru {
        val hostList = listOf("https://safebooru.org")
        val host = HostHttpClient(defaultClient, hostList)
        return Safebooru(host)
    }
}