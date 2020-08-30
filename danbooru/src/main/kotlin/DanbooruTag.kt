import tag.Tag
import tag.Type

data class DanbooruTag(
    override val raw: Map<String, String>,
    override val tagId: Int,
    override val text: String,
    override val type: Type,
    val creationTime: Time,
    val updationTime: Time,
    val isLocked: Boolean,
    val category: Int,
    val postCount: Int
) : Tag