package com.makentoshe.booruchan.network

import java.io.Serializable

/**
 * Client which is used in [Booru] for creating calls to some booru api.
 */
abstract class HttpClient : Serializable {

    /**
     * Creates a get request to [url] with[params] and returns [HttpResult] instance as a result.
     */
    abstract fun get(url: String, params: Map<String, String> = mapOf()): HttpResult

    /**
     * Creates a post request to [url] and returns [HttpResult] instance as a result.
     */
    abstract fun post(url: String, body: ByteArray): HttpResult
}

