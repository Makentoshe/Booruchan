package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.network.HttpResult
import java.io.InputStream
import java.io.Serializable

interface Custom: Serializable {

    fun request(request: String): HttpResult
}