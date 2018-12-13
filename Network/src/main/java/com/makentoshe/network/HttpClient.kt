package com.makentoshe.network

import java.io.Serializable

/**
 * Client which is used in [Booru] for creating calls to some booru api.
 */
abstract class HttpClient: Serializable {

    /**
     * Creates a get request to [url] and returns [HttpGet] instance as a result.
     */
    abstract fun get(url: String): HttpGet

    /**
     * Creates a post request to [url] and returns [HttpPost] instance as a result.
     */
    abstract fun post(url: String, body: ByteArray): HttpPost
}

