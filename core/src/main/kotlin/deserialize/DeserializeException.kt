package deserialize

data class DeserializeException(
    val raw: Map<String, Any?>,
    override val cause: Throwable
): Throwable()