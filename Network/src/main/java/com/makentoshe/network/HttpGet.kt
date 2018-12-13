package com.makentoshe.network

import java.io.InputStream
import java.io.Serializable

/**
 * A http GET request result.
 */
abstract class HttpGet(private val url: String): HttpResult, Serializable {

    /**
     * Returns result as an [InputStream].
     */
    abstract fun stream(): InputStream

    /**
     * Returns result as a [String].
     */
    abstract fun body(): String

}