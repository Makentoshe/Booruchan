package com.makentoshe.booruapi

import java.io.InputStream

abstract class Booru(protected val api: BooruApi) {
    abstract fun customGetRequest(request: String): InputStream
}