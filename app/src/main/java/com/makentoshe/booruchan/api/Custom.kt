package com.makentoshe.booruchan.api

import java.io.InputStream
import java.io.Serializable

interface Custom: Serializable {

    fun request(request: String): InputStream
}