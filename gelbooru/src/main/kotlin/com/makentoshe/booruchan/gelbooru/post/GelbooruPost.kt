package com.makentoshe.booruchan.gelbooru.post

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.makentoshe.booruchan.core.post.*
import com.makentoshe.booruchan.core.text
import com.makentoshe.booruchan.core.time
import java.io.File
import java.io.Serializable

interface GelbooruPost : Post, Serializable {
    val md5: String
    val change: String
}

@JacksonXmlRootElement(localName = "post")
data class XmlGelbooruPost(
    @JsonProperty("id", required = true)
    @JacksonXmlProperty(localName = "id", isAttribute = true)
    override val postId: Int,
    @JacksonXmlProperty(localName = "score", isAttribute = true)
    val rawScore: Int,
    @JacksonXmlProperty(localName = "md5", isAttribute = true)
    override val md5: String,
    @JacksonXmlProperty(localName = "rating", isAttribute = true)
    private val rawRating: String,
    @JacksonXmlProperty(localName = "source", isAttribute = true)
    override val source: String?,
    @JacksonXmlProperty(localName = "created_at", isAttribute = true)
    private val rawCreationTime: String,
    @JacksonXmlProperty(localName = "height", isAttribute = true)
    private val fileHeight: Int,
    @JacksonXmlProperty(localName = "width", isAttribute = true)
    private val fileWidth: Int,
    @JacksonXmlProperty(localName = "file_url", isAttribute = true)
    private val fileUrl: String,
    @JacksonXmlProperty(localName = "sample_height", isAttribute = true)
    private val sampleHeight: Int,
    @JacksonXmlProperty(localName = "sample_width", isAttribute = true)
    private val sampleWidth: Int,
    @JacksonXmlProperty(localName = "sample_url", isAttribute = true)
    private val sampleUrl: String,
    @JacksonXmlProperty(localName = "preview_height", isAttribute = true)
    private val previewHeight: Int,
    @JacksonXmlProperty(localName = "preview_width", isAttribute = true)
    private val previewWidth: Int,
    @JacksonXmlProperty(localName = "preview_url", isAttribute = true)
    private val previewUrl: String,
    @JacksonXmlProperty(localName = "tags", isAttribute = true)
    private val tagsString: String,
    @JacksonXmlProperty(localName = "change", isAttribute = true)
    override val change: String,
    @JacksonXmlProperty(localName = "has_comments", isAttribute = true)
    val hasComments: Boolean,
    @JacksonXmlProperty(localName = "has_notes", isAttribute = true)
    val hasNotes: Boolean,
    @JacksonXmlProperty(localName = "has_children", isAttribute = true)
    val hasChildren: Boolean,
    @JacksonXmlProperty(localName = "parent_id", isAttribute = true)
    val parentId: Int?,
    @JacksonXmlProperty(localName = "status", isAttribute = true)
    val status: String?
) : GelbooruPost, Serializable {
    override val creationTime = time(rawCreationTime)
    override val fullImage = fullImage(fileUrl, fileHeight, fileWidth)
    override val sampleImage = sampleImage(sampleUrl, sampleHeight, sampleWidth)
    override val previewImage = previewImage(previewUrl, previewHeight, previewWidth)
    override val tags = tagsFromText(tagsString.split(" ").map(::text).toSet())
    override val htwRatio: Float = fileHeight.toFloat() / fileWidth.toFloat()

    @JsonIgnore
    override val score = score(rawScore)

    @JsonIgnore
    override val rating = when (rawRating) {
        "q" -> Rating.QUESTIONABLE
        "e" -> Rating.EXPLICIT
        "s" -> Rating.SAFE
        else -> error("Could not define $rawRating rating")
    }
}

data class JsonGelbooruPost(
    @JsonProperty("id", required = true)
    override val postId: Int,
    @JsonProperty("score")
    val rawScore: Int,
    @JsonProperty("hash")
    override val md5: String,
    @JsonProperty("rating")
    val rawRating: String,
    @JsonProperty("source")
    override val source: String?,
    @JsonProperty("created_at")
    val rawCreationTime: String,
    @JsonProperty("height")
    val fileHeight: Int,
    @JsonProperty("width")
    val fileWidth: Int,
    @JsonProperty("file_url")
    val fileUrl: String,
    @JsonProperty("sample_height")
    val sampleHeight: Int,
    @JsonProperty("sample_width")
    val sampleWidth: Int,
    @JsonProperty("tags")
    val tagString: String,
    @JsonProperty("change")
    override val change: String,
    @JsonProperty("owner")
    val owner: String,
    @JsonProperty("parent_id")
    val parentId: Int?,
    @JsonProperty("sample")
    val sample: Int,
    @JsonProperty("image")
    val image: String,
    @JsonProperty("directory")
    val directory: String
) : GelbooruPost, Serializable {
    override val creationTime = time(rawCreationTime)
    override val fullImage = fullImage(fileUrl, fileHeight, fileWidth)
    override val sampleImage = internalSampleImage()
    override val previewImage = internalPreviewImage()
    override val tags = tagsFromText(tagString.split(" ").map(::text).toSet())
    override val htwRatio: Float = fileHeight.toFloat() / fileWidth.toFloat()

    @JsonIgnore
    override val score = score(rawScore)

    private fun internalSampleImage(): SampleImage {
        val imageFile = File(image)
        val extension = when (imageFile.extension) {
            "png" -> "jpg"
            else -> imageFile.extension
        }
        val sampleUrlPart = File("sample_${imageFile.nameWithoutExtension}.$extension")
        val url = "https://img2.gelbooru.com/samples/$directory/$sampleUrlPart"
        return sampleImage(url)
    }

    private fun internalPreviewImage(): PreviewImage {
        val imageFile = File("thumbnail_$image")
        val url = "https://img1.gelbooru.com/thumbnails/$directory/${imageFile.nameWithoutExtension}.jpg"
        return previewImage(url)
    }

    @JsonIgnore
    override val rating = when (rawRating) {
        "q" -> Rating.QUESTIONABLE
        "e" -> Rating.EXPLICIT
        "s" -> Rating.SAFE
        else -> error("Could not define $rawRating rating")
    }
}
