package com.makentoshe.booruchan.danbooru.post

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.makentoshe.booruchan.core.Time
import com.makentoshe.booruchan.core.post.*
import com.makentoshe.booruchan.core.text
import com.makentoshe.booruchan.core.time
import java.io.File
import java.io.Serializable

interface DanbooruPost : Post, Serializable {
    val updationTime: Time?

    val uploaderId: Int

    val approverId: Int?
    val bitFlags: Int
    val favCount: Int
    val fileSize: Int

    val fileExtension: String
    val lastCommentBumpTime: Time?
    val lastCommentTime: Time?
    val lastNoteTime: Time?
    val parentId: Int?
    val pixivId: Int?
    val poolString: String?
    val md5: String
    val isBanned: Boolean
    val isDeleted: Boolean
    val isFavorited: Boolean
    val isFlagged: Boolean
    val isNoteLocked: Boolean
    val isPending: Boolean
    val isRatingLocked: Boolean
    val isStatusLocked: Boolean
    val hasLarge: Boolean
    val hasChildren: Boolean
    val hasActiveChildren: Boolean
    val hasVisibleChildren: Boolean
}

data class JsonDanbooruPost(
    @JsonProperty("id")
    override val postId: Int,
    @JsonProperty("score")
    val rawScore: Int,
    @JsonProperty("down_score")
    val rawDownScore: Int,
    @JsonProperty("up_score")
    val rawUpScore: Int,
    @JsonProperty("created_at")
    val rawCreationTime: String,
    @JsonProperty("updated_at")
    val rawUpdationTime: String?,
    @JsonProperty("last_comment_bumped_at")
    val rawLastCommentBumpTime: String?,
    @JsonProperty("last_commented_at")
    val rawLastCommentTime: String?,
    @JsonProperty("last_noted_at")
    val rawLastNoteTime: String?,
    @JsonProperty("uploader_id")
    override val uploaderId: Int,
    @JsonProperty("approver_id")
    override val approverId: Int?,
    @JsonProperty("bit_flags")
    override val bitFlags: Int,
    @JsonProperty("fav_count")
    override val favCount: Int,
    @JsonProperty("file_size")
    override val fileSize: Int,
    @JsonProperty("file_ext")
    val rawFileExtension: String?,
    @JsonProperty("parent_id")
    override val parentId: Int?,
    @JsonProperty("pixiv_id")
    override val pixivId: Int?,
    @JsonProperty("pool_string")
    override val poolString: String?,
    @JsonProperty("md5")
    override val md5: String,
    @JsonProperty("rating")
    val rawRating: String,
    @JsonProperty("source")
    override val source: String?,
    @JsonProperty("is_banned")
    override val isBanned: Boolean,
    @JsonProperty("is_deleted")
    override val isDeleted: Boolean,
    @JsonProperty("is_favorited")
    override val isFavorited: Boolean,
    @JsonProperty("is_flagged")
    override val isFlagged: Boolean,
    @JsonProperty("is_note_locked")
    override val isNoteLocked: Boolean,
    @JsonProperty("is_pending")
    override val isPending: Boolean,
    @JsonProperty("is_rating_locked")
    override val isRatingLocked: Boolean,
    @JsonProperty("is_status_locked")
    override val isStatusLocked: Boolean,
    @JsonProperty("has_large")
    override val hasLarge: Boolean,
    @JsonProperty("has_children")
    override val hasChildren: Boolean,
    @JsonProperty("has_active_children")
    override val hasActiveChildren: Boolean,
    @JsonProperty("has_visible_children")
    override val hasVisibleChildren: Boolean,
    @JsonProperty("tag_string")
    val tagString: String,
    @JsonProperty("tag_count_general")
    val tagCountGeneral: Int,
    @JsonProperty("tag_count_artist")
    val tagCountArtist: Int,
    @JsonProperty("tag_count_character")
    val tagCountCharacter: Int,
    @JsonProperty("tag_count_copyright")
    val tagCountCopyright: Int,
    @JsonProperty("tag_count_meta")
    val tagCountMeta: Int,
    @JsonProperty("tag_count")
    val tagCount: Int,
    @JsonProperty("tag_string_general")
    val tagStringGeneral: String?,
    @JsonProperty("tag_string_character")
    val tagStringCharacter: String?,
    @JsonProperty("tag_string_copyright")
    val tagStringCopyright: String?,
    @JsonProperty("tag_string_artist")
    val tagStringArtist: String?,
    @JsonProperty("tag_string_meta")
    val tagStringMeta: String?,
    @JsonProperty("image_width")
    val imageWidth: Int,
    @JsonProperty("image_height")
    val imageHeight: Int,
    @JsonProperty("large_file_url")
    val fileUrl: String,
    @JsonProperty("file_url")
    val sampleUrl: String,
    @JsonProperty("preview_file_url")
    val previewUrl: String
) : DanbooruPost, Serializable {
    override val creationTime = time(rawCreationTime)
    override val updationTime = rawUpdationTime?.let(::time)
    override val lastNoteTime = rawLastNoteTime?.let(::time)
    override val lastCommentTime = rawLastCommentTime?.let(::time)
    override val lastCommentBumpTime = rawLastCommentBumpTime?.let(::time)
    override val tags = tagsFromText(tagString.split(" ").map(::text).toSet())
    override val previewContent = previewContent(previewUrl)
    override val sampleContent = sampleContent(sampleUrl)
    override val fullContent = fullContent(fileUrl, imageHeight, imageWidth)
    override val htwRatio: Float = imageHeight.toFloat() / imageWidth.toFloat()

    @JsonIgnore
    override val score = score(rawUpScore, rawDownScore)

    @JsonIgnore
    override val fileExtension = rawFileExtension ?: File(fileUrl).extension

    @JsonIgnore
    override val rating = when (rawRating) {
        "q" -> Rating.QUESTIONABLE
        "e" -> Rating.EXPLICIT
        "s" -> Rating.SAFE
        else -> error("Could not define $rawRating rating")
    }
}

@JacksonXmlRootElement(localName = "post")
data class XmlDanbooruPost(
    @JacksonXmlProperty(localName = "id")
    override val postId: Int,
    @JacksonXmlProperty(localName = "score")
    val rawScore: Int,
    @JacksonXmlProperty(localName = "down-score")
    val rawDownScore: Int,
    @JacksonXmlProperty(localName = "up-score")
    val rawUpScore: Int,
    @JacksonXmlProperty(localName = "created-at")
    val rawCreationTime: String,
    @JacksonXmlProperty(localName = "updated-at")
    val rawUpdationTime: String?,
    @JacksonXmlProperty(localName = "last-comment-bumped-at")
    val rawLastCommentBumpTime: String?,
    @JacksonXmlProperty(localName = "last-commented-at")
    val rawLastCommentTime: String?,
    @JacksonXmlProperty(localName = "last-noted-at")
    val rawLastNoteTime: String?,
    @JacksonXmlProperty(localName = "uploader-id")
    override val uploaderId: Int,
    @JacksonXmlProperty(localName = "approver-id")
    override val approverId: Int?,
    @JacksonXmlProperty(localName = "bit-flags")
    override val bitFlags: Int,
    @JacksonXmlProperty(localName = "fav-count")
    override val favCount: Int,
    @JacksonXmlProperty(localName = "file-size")
    override val fileSize: Int,
    @JacksonXmlProperty(localName = "file-ext")
    val rawFileExtension: String,
    @JacksonXmlProperty(localName = "parent-id")
    override val parentId: Int?,
    @JacksonXmlProperty(localName = "pixiv-id")
    override val pixivId: Int?,
    @JacksonXmlProperty(localName = "pool-string")
    override val poolString: String?,
    @JacksonXmlProperty(localName = "md5")
    override val md5: String,
    @JacksonXmlProperty(localName = "rating")
    val rawRating: String,
    @JacksonXmlProperty(localName = "source")
    override val source: String?,
    @JacksonXmlProperty(localName = "is-banned")
    override val isBanned: Boolean,
    @JacksonXmlProperty(localName = "is-deleted")
    override val isDeleted: Boolean,
    @JacksonXmlProperty(localName = "is-favorited")
    override val isFavorited: Boolean,
    @JacksonXmlProperty(localName = "is-flagged")
    override val isFlagged: Boolean,
    @JacksonXmlProperty(localName = "is-note-locked")
    override val isNoteLocked: Boolean,
    @JacksonXmlProperty(localName = "is-pending")
    override val isPending: Boolean,
    @JacksonXmlProperty(localName = "is-rating-locked")
    override val isRatingLocked: Boolean,
    @JacksonXmlProperty(localName = "is-status-locked")
    override val isStatusLocked: Boolean,
    @JacksonXmlProperty(localName = "has-large")
    override val hasLarge: Boolean,
    @JacksonXmlProperty(localName = "has-children")
    override val hasChildren: Boolean,
    @JacksonXmlProperty(localName = "has-active-children")
    override val hasActiveChildren: Boolean,
    @JacksonXmlProperty(localName = "has-visible-children")
    override val hasVisibleChildren: Boolean,
    @JacksonXmlProperty(localName = "tag-string")
    val tagString: String,
    @JacksonXmlProperty(localName = "tag-count-general")
    val tagCountGeneral: Int,
    @JacksonXmlProperty(localName = "tag-count-artist")
    val tagCountArtist: Int,
    @JacksonXmlProperty(localName = "tag-count-character")
    val tagCountCharacter: Int,
    @JacksonXmlProperty(localName = "tag-count-copyright")
    val tagCountCopyright: Int,
    @JacksonXmlProperty(localName = "tag-count-meta")
    val tagCountMeta: Int,
    @JacksonXmlProperty(localName = "tag-count")
    val tagCount: Int,
    @JacksonXmlProperty(localName = "tag-string-general")
    val tagStringGeneral: String?,
    @JacksonXmlProperty(localName = "tag-string-character")
    val tagStringCharacter: String?,
    @JacksonXmlProperty(localName = "tag-string-copyright")
    val tagStringCopyright: String?,
    @JacksonXmlProperty(localName = "tag-string-artist")
    val tagStringArtist: String?,
    @JacksonXmlProperty(localName = "tag-string-meta")
    val tagStringMeta: String?,
    @JacksonXmlProperty(localName = "image-width")
    val imageWidth: Int,
    @JacksonXmlProperty(localName = "image-height")
    val imageHeight: Int,
    @JacksonXmlProperty(localName = "large-file-url")
    val fileUrl: String,
    @JacksonXmlProperty(localName = "file-url")
    val sampleUrl: String,
    @JacksonXmlProperty(localName = "preview-file-url")
    val previewUrl: String
) : DanbooruPost, Serializable {
    override val creationTime = time(rawCreationTime)
    override val updationTime = rawUpdationTime?.let(::time)
    override val lastNoteTime = rawLastNoteTime?.let(::time)
    override val lastCommentTime = rawLastCommentTime?.let(::time)
    override val lastCommentBumpTime = rawLastCommentBumpTime?.let(::time)
    override val tags = tagsFromText(tagString.split(" ").map(::text).toSet())
    override val previewContent = previewContent(previewUrl)
    override val sampleContent = sampleContent(sampleUrl)
    override val fullContent = fullContent(fileUrl, imageHeight, imageWidth)
    override val htwRatio: Float = imageHeight.toFloat() / imageWidth.toFloat()

    @JsonIgnore
    override val score = score(rawUpScore, rawDownScore)

    @JsonIgnore
    override val fileExtension = rawFileExtension ?: File(fileUrl).extension

    @JsonIgnore
    override val rating = when (rawRating) {
        "q" -> Rating.QUESTIONABLE
        "e" -> Rating.EXPLICIT
        "s" -> Rating.SAFE
        else -> error("Could not define $rawRating rating")
    }
}
