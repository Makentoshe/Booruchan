package tag

interface TagsFormatter<T: Tag> {
    fun deserialize(string: String): Tags<T>
}