package deserialize

class EntityDeserializeException(
    val raw: Map<String, Any?>,
    override val cause: Throwable
) : DeserializeException(cause, "Could not deserialize entity")