package com.makentoshe.controllers

/**
 * Any download will be wrapped in this class.
 */
class DownloadResult<T> private constructor(private val _data: T? = null, private val _exception: Exception? = null) {
    /**
     * Constructor for putting a data to the result.
     */
    constructor(data: T) : this(data, null)

    /**
     * Constructor for putting an exception to the result.
     */
    constructor(exception: Exception) : this(null, exception)

    /**
     * Property for returning a not null data.
     */
    val data: T
        get() = _data!!

    /**
     * Property for returning a not null exception.
     */
    val exception: Exception
        get() = _exception!!

    /**
     * Checks that data is not a null.
     */
    fun hasData() = _data != null

    /**
     * Checks that exception is not a null.
     */
    fun hasException() = _exception != null
}

