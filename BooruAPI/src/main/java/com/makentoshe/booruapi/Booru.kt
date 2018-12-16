package com.makentoshe.booruapi

import java.io.InputStream

abstract class Booru(protected val api: BooruApi) {
    abstract fun customGet(request: String): InputStream
    abstract fun autocomplete(term: String): List<Tag>
}