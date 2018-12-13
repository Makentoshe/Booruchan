package com.makentoshe.network

import java.io.Serializable

interface HttpResult: Serializable {

    val isSuccessful
        get() = (code() / 100) == 2

    val isClientError
        get() = (code() / 100) == 4

    val isServerError
        get() = (code() / 100) == 5


    /**
     * Returns a code.
     */
    abstract fun code(): Int

    /**
     * Returns a message.
     */
    abstract fun message(): String

}