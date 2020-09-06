package post

import Time
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import text
import time

interface DanbooruPost : PostId {
    val score: Int
    val downScore: Int
    val upScore: Int

    val creationTime: Time
    val updationTime: Time?

    val fullImage: FullImage
    val sampleImage: SampleImage
    val previewImage: PreviewImage

    val uploaderId: Int

    val approverId: Int?
    val bitFlags: Int
    val favCount: Int
    val fileSize: Int

    val lastCommentBumpTime: Time?
    val lastCommentTime: Time?
    val lastNoteTime: Time?
    val parentId: Int?
    val pixivId: Int?
    val poolString: String?
    val md5: String
    val rating: Rating
    val source: String?
    val tags: Tags
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
    override val score: Int,
    override val downScore: Int,
    override val upScore: Int,
    override val creationTime: Time,
    override val updationTime: Time?,
    override val fullImage: FullImage,
    override val sampleImage: SampleImage,
    override val previewImage: PreviewImage,
    override val uploaderId: Int,
    override val approverId: Int?,
    override val bitFlags: Int,
    override val favCount: Int,
    override val fileSize: Int,
    override val lastCommentBumpTime: Time?,
    override val lastCommentTime: Time?,
    override val lastNoteTime: Time?,
    override val parentId: Int,
    override val pixivId: Int,
    override val poolString: String,
    override val md5: String,
    override val rating: Rating,
    override val source: String,
    override val tags: Tags,
    override val isBanned: Boolean,
    override val isDeleted: Boolean,
    override val isFavorited: Boolean,
    override val isFlagged: Boolean,
    override val isNoteLocked: Boolean,
    override val isPending: Boolean,
    override val isRatingLocked: Boolean,
    override val isStatusLocked: Boolean,
    override val hasLarge: Boolean,
    override val hasChildren: Boolean,
    override val hasActiveChildren: Boolean,
    override val hasVisibleChildren: Boolean,
    override val postId: Int
) : DanbooruPost

@JacksonXmlRootElement(localName = "post")
data class XmlDanbooruPost(
    @JacksonXmlProperty(localName = "id")
    override val postId: Int,
    @JacksonXmlProperty(localName = "score")
    override val score: Int,
    @JacksonXmlProperty(localName = "down-score")
    override val downScore: Int,
    @JacksonXmlProperty(localName = "up-score")
    override val upScore: Int,
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
    val fileExtension: String,
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
) : DanbooruPost {
    override val creationTime = time(rawCreationTime)
    override val updationTime = rawUpdationTime?.let(::time)
    override val lastNoteTime = rawLastNoteTime?.let(::time)
    override val lastCommentTime = rawLastCommentTime?.let(::time)
    override val lastCommentBumpTime = rawLastCommentBumpTime?.let(::time)
    override val tags = tags(tagString.split(" ").map(::text).toSet())
    override val previewImage = previewImage(previewUrl)
    override val sampleImage = sampleImage(sampleUrl)
    override val fullImage = fullImage(fileUrl, imageHeight, imageWidth)

    @JsonIgnore
    override val rating = when (rawRating) {
        "q" -> Rating.QUESTIONABLE
        "e" -> Rating.EXPLICIT
        "s" -> Rating.SAFE
        else -> error("Could not define $rawRating rating")
    }
}
