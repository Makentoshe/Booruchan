package com.makentoshe.booruchan.gelbooru.tag

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type

interface GelbooruTag : Tag {
    val count: Int
    val ambiguous: Boolean
}

@JacksonXmlRootElement(localName = "tags")
data class XmlGelbooruTag(
    @JsonProperty("id", required = true)
    @JacksonXmlProperty(localName = "id", isAttribute = true)
    override val tagId: Int,
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    override val text: String,
    @JacksonXmlProperty(localName = "type", isAttribute = true)
    val rawType: String,
    @JacksonXmlProperty(localName = "count", isAttribute = true)
    override val count: Int,
    @JacksonXmlProperty(localName = "ambiguous", isAttribute = true)
    override val ambiguous: Boolean
): GelbooruTag {

    @JsonIgnore
    override val type: Type = when(rawType) {
        else -> Type.UNDEFINED
    }
}

data class JsonGelbooruTag(
    @JsonProperty("id", required = true)
    override val tagId: Int,
    @JsonProperty("tag", required = true)
    override val text: String,
    @JsonProperty("type", required = true)
    val rawType: String,
    @JsonProperty("count", required = true)
    override val count: Int,
    @JsonProperty("ambiguous", required = true)
    val rawAmbiguous: Int
): GelbooruTag {

    @JsonIgnore
    override val ambiguous = rawAmbiguous != 0

    @JsonIgnore
    override val type: Type = when(rawType) {
        else -> Type.UNDEFINED
    }
}
