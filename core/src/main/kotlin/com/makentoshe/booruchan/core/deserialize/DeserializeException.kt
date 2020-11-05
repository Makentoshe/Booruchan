package com.makentoshe.booruchan.core.deserialize

open class DeserializeException(
    open val rawValue: String,
    override val cause: Throwable,
    override val message: String? = null
): Throwable()
