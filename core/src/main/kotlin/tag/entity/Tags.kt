package tag.entity

interface Tags<out T : Tag> {
    val tags: List<T>
}