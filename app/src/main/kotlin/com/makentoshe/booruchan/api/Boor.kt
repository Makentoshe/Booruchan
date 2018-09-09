package com.makentoshe.booruchan.api

abstract class Boor(private val requestAPI: BoorRequestAPI): BoorNetwork(requestAPI) {

    abstract fun getBooruName(): String

    abstract fun convertLocalTimeToDefault(time: String): String

    fun getApi(): BoorRequestAPI {
        return requestAPI
    }

}