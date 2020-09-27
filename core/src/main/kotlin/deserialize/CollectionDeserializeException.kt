package deserialize

data class CollectionDeserializeException(
    override val cause: Throwable
): Throwable()