import tag.Tag

data class DanbooruTag(
    override val raw: Map<String, String>,
    override val tagId: Int,
    override val text: String,
    val creationTime: Time,
    val updationTime: Time,
    val isLocked: Boolean,
    val category: Int,
    val postCount: Int
) : Tag