package tag.deserialize

import tag.DanbooruTag
import tag.JsonDanbooruTag
import tag.XmlDanbooruTag

typealias XmlDanbooruTagDeserialize = DanbooruTagDeserialize<XmlDanbooruTag>
typealias JsonDanbooruTagDeserialize = DanbooruTagDeserialize<JsonDanbooruTag>

data class DanbooruTagDeserialize<out Tag : DanbooruTag>(
    override val tag: Tag
) : TagDeserialize<Tag>
