package com.makentoshe.booruchan.network

import java.io.InputStream
import java.io.Serializable
import java.net.URL

interface HttpResult : Serializable {

    val isSuccessful
        get() = (code / 100) == 2

    val isClientError
        get() = (code / 100) == 4

    val isServerError
        get() = (code / 100) == 5

    /**
     * Returns result as an [InputStream].
     */
    val stream: InputStream

    /**
     * Returns result as a [String].
     */
    val body: String

    /**
     * Returns a code.
     */
    val code: Int

    /**
     * Returns a message.
     */
    val message: String

    /**
     * Returns an request url
     */
    val url: URL

    /**
     * Returns a content length in bytes
     */
    val length: Long
}