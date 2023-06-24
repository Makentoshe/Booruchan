package com.makentoshe.booruchan.core.deserialize

@Deprecated("Should be replaced with function")
data class CollectionDeserializeException(
    override val rawValue: String, override val cause: Throwable
) : DeserializeException(rawValue, cause, "Could not deserialize collection")

// todo replace class with this function
fun collectionDeserializeException(rawValue: String, cause: Throwable, type: String? = null) =
    DeserializeException(rawValue, cause, "Could not deserialize ${type ?: ""} collection")
