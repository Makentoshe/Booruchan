import tag.Tag
import tag.Type

data class GelbooruTag(
    override val raw: Map<String, String>,
    override val text: String,
    override val tagId: Int,
    override val type: Type,
    val count: Int,
    val ambiguous: Boolean
) : Tag