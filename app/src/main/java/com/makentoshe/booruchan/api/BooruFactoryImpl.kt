package com.makentoshe.booruchan.api

import android.content.Context
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.decorator.ProxyHttpClient

class BooruFactoryImpl(private val defaultClient: HttpClient) : BooruFactory {

    override fun buildBooru(`class`: Class<out Booru>, context: Context): Booru {
        return when (`class`) {
            Gelbooru::class.java -> buildGelbooru(context)
            Safebooru::class.java -> buildSafebooru(context)
            else -> throw IllegalArgumentException()
        }
    }

    private fun buildGelbooru(context: Context): Booru {
        val proxy = ProxyHttpClient(defaultClient, "http://service.bypass123.com/index.php")
        return Gelbooru(proxy)
    }

    private fun buildSafebooru(context: Context): Booru {
        val proxy = ProxyHttpClient(defaultClient, "http://service.bypass123.com/index.php")
        return Safebooru(proxy)
    }
}