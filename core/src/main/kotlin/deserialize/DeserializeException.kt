package deserialize

open class DeserializeException(override val cause: Throwable, override val message: String? = null): Throwable()
