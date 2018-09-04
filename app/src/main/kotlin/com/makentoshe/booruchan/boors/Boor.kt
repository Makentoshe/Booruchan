package com.makentoshe.booruchan.boors

abstract class Boor(requestAPI: BoorRequestAPI): BoorNetwork(requestAPI) {

    abstract fun getBooruName(): String

    abstract fun convertLocalTimeToDefault(time: String): String

}