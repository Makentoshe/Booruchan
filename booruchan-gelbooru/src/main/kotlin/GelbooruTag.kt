import tag.Ambiguous
import tag.Count
import tag.Tag
import tag.Type

data class GelbooruTag(
    override val raw: Map<String, String>,
    override val text: String,
    override val ambiguous: Boolean,
    override val count: Int,
    override val tagId: Int,
    override val type: Any
) : Tag, Type, Count, Ambiguous