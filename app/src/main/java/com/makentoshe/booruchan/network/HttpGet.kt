package com.makentoshe.booruchan.network

import java.io.InputStream
import java.io.Serializable

/**
 * A http GET request result.
 */
interface HttpGet : HttpResult, Serializable {

    /**
     * Returns result as an [InputStream].
     */
    fun stream(): InputStream

    /**
     * Returns result as a [String].
     */
    fun body(): String

}