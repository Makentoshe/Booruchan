import tag.Tag
import tag.Type

data class DanbooruTag(
    override val rawString: String,
    override val rawMap: Map<String, String>,
    override val tagId: Int,
    override val text: String,
    override val type: Type,
    val creationTime: Time,
    val updationTime: Time,
    val isLocked: Boolean,
    val category: Int,
    val postCount: Int
) : Tag