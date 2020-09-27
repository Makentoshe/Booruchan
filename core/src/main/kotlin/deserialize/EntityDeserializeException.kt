package deserialize

data class EntityDeserializeException(
    val raw: Map<String, Any?>,
    override val cause: Throwable
): Throwable()