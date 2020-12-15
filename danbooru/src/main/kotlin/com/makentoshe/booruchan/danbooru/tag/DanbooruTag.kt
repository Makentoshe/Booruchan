package com.makentoshe.booruchan.danbooru.tag

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.makentoshe.booruchan.core.Time
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type
import com.makentoshe.booruchan.core.time
import java.io.Serializable

interface DanbooruTag : Tag, Serializable {
    val creationTime: Time
    val updationTime: Time?
    val isLocked: Boolean
    val count: Int
    val rawType: Int
    val rawCreationTime: String
    val rawUpdationTime: String?
}

@JacksonXmlRootElement(localName = "tags")
data class XmlDanbooruTag(
    @JsonProperty("id", required = true)
    @JacksonXmlProperty(localName = "id")
    override val tagId: Int,
    @JacksonXmlProperty(localName = "name")
    override val text: String,
    @JacksonXmlProperty(localName = "type")
    override val rawType: Int,
    @JacksonXmlProperty(localName = "created-at")
    override val rawCreationTime: String,
    @JacksonXmlProperty(localName = "updated-at")
    override val rawUpdationTime: String?,
    @JacksonXmlProperty(localName = "is-locked")
    override val isLocked: Boolean,
    @JacksonXmlProperty(localName = "post-count")
    override val count: Int
) : DanbooruTag, Serializable {
    override val creationTime = rawCreationTime.let(::time)
    override val updationTime = rawUpdationTime?.let(::time)

    @JsonIgnore
    override val type = when (rawType) {
        1 -> Type.ARTIST
        3 -> Type.COPYRIGHT
        4 -> Type.CHARACTER
        5 -> Type.METADATA
        else -> Type.GENERAL
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as XmlDanbooruTag

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

data class JsonDanbooruTag(
    @JsonProperty("id", required = true)
    override val tagId: Int,
    @JsonProperty("name")
    override val text: String,
    @JsonProperty("category")
    override val rawType: Int,
    @JsonProperty("created_at")
    override val rawCreationTime: String,
    @JsonProperty("updated_at")
    override val rawUpdationTime: String?,
    @JsonProperty("is_locked")
    override val isLocked: Boolean,
    @JsonProperty("post_count")
    override val count: Int
) : DanbooruTag, Serializable {
    override val creationTime = rawCreationTime.let(::time)
    override val updationTime = rawUpdationTime?.let(::time)

    override val type = when (rawType) {
        1 -> Type.ARTIST
        3 -> Type.COPYRIGHT
        4 -> Type.CHARACTER
        5 -> Type.METADATA
        else -> Type.GENERAL
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JsonDanbooruTag

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
