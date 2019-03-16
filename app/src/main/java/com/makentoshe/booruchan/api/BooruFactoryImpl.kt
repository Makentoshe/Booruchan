package com.makentoshe.booruchan.api

import android.content.Context
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.network.HostHttpClient
import com.makentoshe.booruchan.network.HttpClient

class BooruFactoryImpl(private val defaultClient: HttpClient) : BooruFactory {

    override fun buildBooru(`class`: Class<out Booru>, context: Context): Booru {
        return when (`class`) {
            Gelbooru::class.java -> buildGelbooru(context)
            else -> throw IllegalArgumentException()
        }
    }

    private fun buildGelbooru(context: Context): Booru {
        val hostList = mutableListOf("https://www.gelbooru.com")
//        hostList.add("http://0s.m5swyytpn5zhkltdn5wq.nblz.ru")
//        todo(define the mirrors or get them from the storage using context)
//        val proxy = ProxyHttpClient(defaultClient, listOf("http://service.bypass123.com/index.php"))
        val host = HostHttpClient(defaultClient, hostList)
        return Gelbooru(host)
    }
}