package com.makentoshe.booruchan.common.api

import java.io.Serializable

abstract class Boor(private val requestAPI: BoorRequestAPI, client: HttpClient): BoorNetwork(requestAPI, client), Serializable {

    abstract fun getBooruName(): String

    abstract fun convertLocalTimeToDefault(time: String): String

    fun getApi(): BoorRequestAPI {
        return requestAPI
    }

}