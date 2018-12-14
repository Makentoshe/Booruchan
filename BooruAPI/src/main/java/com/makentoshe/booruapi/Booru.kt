package com.makentoshe.booruapi

abstract class Booru(protected val booruApi: BooruApi) {
    abstract fun customRequest(request: String)
}