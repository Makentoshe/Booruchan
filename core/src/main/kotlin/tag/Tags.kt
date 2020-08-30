package tag

interface Tags<out T : Tag> {
    val tags: List<T>
}