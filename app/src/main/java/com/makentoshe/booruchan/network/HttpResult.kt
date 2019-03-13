package com.makentoshe.booruchan.network

import java.io.Serializable

interface HttpResult : Serializable {

    val isSuccessful
        get() = (code() / 100) == 2

    val isClientError
        get() = (code() / 100) == 4

    val isServerError
        get() = (code() / 100) == 5


    /**
     * Returns a code.
     */
    fun code(): Int

    /**
     * Returns a message.
     */
    fun message(): String

}