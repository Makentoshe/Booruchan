package com.makentoshe.booruchan.api

import java.io.Serializable

abstract class Boor(private val requestAPI: BoorRequestAPI): BoorNetwork(requestAPI), Serializable {

    abstract fun getBooruName(): String

    abstract fun convertLocalTimeToDefault(time: String): String

    fun getApi(): BoorRequestAPI {
        return requestAPI
    }

}