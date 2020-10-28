package com.makentoshe.booruchan.core.deserialize

@Deprecated("Should be replaced with function")
data class CollectionDeserializeException(
    override val cause: Throwable
) : DeserializeException(cause, "Could not deserialize collection")

// todo replace class with this function
fun collectionDeserializeException(cause: Throwable, type: String? = null) =
    DeserializeException(cause, "Could not deserialize ${type ?: ""} collection")
