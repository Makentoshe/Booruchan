package tag

import org.jsoup.Jsoup

class XmlGelbooruTagsFormatter : TagsFormatter<GelbooruTag> {

    /**
     * Deserializes xml string to a list of [GelbooruTag].
     * If tags was not defined the empty list will be returned.
     * If tags was defined but some of mandatory attributes was not found
     * the [IllegalStateException] will be thrown
     */
    override fun deserialize(string: String): GelbooruTags {
        val tags = Jsoup.parse(string).body().getElementsByTag("tag").map { tag ->
            val attributes = tag.attributes().associate { it.key to it.value }
            deserializeTag(tag.outerHtml(), attributes)
        }
        return GelbooruTags(tags)
    }

    private fun deserializeTag(string: String, rawMap: Map<String, String>): GelbooruTag {
        val text = rawMap[GelbooruTag.TEXT] ?: deserializeError(GelbooruTag.TEXT)
        val tagId = rawMap[GelbooruTag.TAG_ID]?.toIntOrNull() ?: deserializeError(GelbooruTag.TAG_ID)
        val type = deserializeTagType(rawMap[GelbooruTag.TYPE])
        val count = rawMap[GelbooruTag.COUNT]?.toIntOrNull()
        val ambiguous = deserializeTagAmbiguous(rawMap[GelbooruTag.AMBIGUOUS])
        return GelbooruTag(string, emptyMap(), text, tagId, type, count, ambiguous)
    }

    // fixme implement type mapping
    private fun deserializeTagType(type: String?): Type {
        return Type.UNDEFINED
    }

    private fun deserializeTagAmbiguous(ambiguous: String?): Boolean? {
        if (ambiguous == null) return null
        return ambiguous.toBoolean()
    }

    private fun deserializeError(param: String): Nothing {
        error("Could not define $param attribute")
    }
}
