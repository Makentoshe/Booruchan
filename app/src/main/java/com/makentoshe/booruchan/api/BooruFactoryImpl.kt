package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.rule34.Rule34
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.decorator.ProxyHttpClient

class BooruFactoryImpl(private val defaultClient: HttpClient) : BooruFactory {

    override fun buildBooru(`class`: Class<out Booru>): Booru {
        return when (`class`) {
            Gelbooru::class.java -> buildGelbooru()
            Safebooru::class.java -> buildSafebooru()
            Rule34::class.java -> buildRule34()
            else -> throw IllegalArgumentException()
        }
    }

    private fun buildGelbooru(): Booru {
        val proxy = ProxyHttpClient(defaultClient, "http://service.bypass123.com/index.php")
        return Gelbooru(proxy)
    }

    private fun buildSafebooru(): Booru {
        val proxy = ProxyHttpClient(defaultClient, "http://service.bypass123.com/index.php")
        return Safebooru(proxy)
    }

    private fun buildRule34(): Booru {
        val proxy = ProxyHttpClient(defaultClient, "http://service.bypass123.com/index.php")
        return Rule34(proxy)
    }
}