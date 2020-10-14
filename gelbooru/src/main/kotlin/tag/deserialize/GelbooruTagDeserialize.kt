package tag.deserialize

import tag.GelbooruTag
import tag.JsonGelbooruTag
import tag.XmlGelbooruTag

typealias XmlGelbooruTagDeserialize = GelbooruTagDeserialize<XmlGelbooruTag>
typealias JsonGelbooruTagDeserialize = GelbooruTagDeserialize<JsonGelbooruTag>

data class GelbooruTagDeserialize<out Tag : GelbooruTag>(
    override val tag: Tag
) : TagDeserialize<Tag>
