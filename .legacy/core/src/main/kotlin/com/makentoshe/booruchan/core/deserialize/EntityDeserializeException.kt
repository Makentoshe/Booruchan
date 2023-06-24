package com.makentoshe.booruchan.core.deserialize

class EntityDeserializeException(
    override val rawValue: String,
    val raw: Map<String, Any?>,
    override val cause: Throwable
) : DeserializeException(rawValue, cause, "Could not deserialize entity")