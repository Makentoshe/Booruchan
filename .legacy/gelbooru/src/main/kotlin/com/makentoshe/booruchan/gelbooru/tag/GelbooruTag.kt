package com.makentoshe.booruchan.gelbooru.tag

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type
import java.io.Serializable

interface GelbooruTag : Tag, Serializable {
    val count: Int
    val ambiguous: Boolean
    val rawType: String
}

@JacksonXmlRootElement(localName = "tags")
data class XmlGelbooruTag(
    @JsonProperty("id", required = true)
    @JacksonXmlProperty(localName = "id", isAttribute = true)
    override val tagId: Int,
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    override val text: String,
    @JacksonXmlProperty(localName = "type", isAttribute = true)
    override val rawType: String,
    @JacksonXmlProperty(localName = "count", isAttribute = true)
    override val count: Int,
    @JacksonXmlProperty(localName = "ambiguous", isAttribute = true)
    override val ambiguous: Boolean
): GelbooruTag, Serializable {

    @JsonIgnore
    override val type = when (rawType) {
        "1" -> Type.ARTIST
        "3" -> Type.COPYRIGHT
        "4" -> Type.CHARACTER
        "5" -> Type.METADATA
        else -> Type.GENERAL
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as XmlGelbooruTag

        if (tagId != other.tagId) return false
        if (text != other.text) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tagId
        result = 31 * result + text.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}

data class JsonGelbooruTag(
    @JsonProperty("id", required = true)
    override val tagId: Int,
    @JsonProperty("tag", required = true)
    override val text: String,
    @JsonProperty("type", required = true)
    override val rawType: String,
    @JsonProperty("count", required = true)
    override val count: Int,
    @JsonProperty("ambiguous", required = true)
    val rawAmbiguous: Int
): GelbooruTag, Serializable {

    @JsonIgnore
    override val ambiguous = rawAmbiguous != 0

    @JsonIgnore
    override val type = when (rawType) {
        "1" -> Type.ARTIST
        "3" -> Type.COPYRIGHT
        "4" -> Type.CHARACTER
        "5" -> Type.METADATA
        else -> Type.GENERAL
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JsonGelbooruTag

        if (tagId != other.tagId) return false
        if (text != other.text) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tagId
        result = 31 * result + text.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}
