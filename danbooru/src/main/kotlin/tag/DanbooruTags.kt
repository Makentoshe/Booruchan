package tag

interface DanbooruTags<out Tag : DanbooruTag> {
    val tags: List<Tag>
}

data class XmlDanbooruTags(override val tags: List<XmlDanbooruTag>) : DanbooruTags<XmlDanbooruTag>

data class JsonDanbooruTags(override val tags: List<JsonDanbooruTag>) : DanbooruTags<JsonDanbooruTag>