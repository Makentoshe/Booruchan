package post

import Time

interface DanbooruPost: PostId {
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
    val parentId: Int
    val pixivId: Int
    val poolString: String
    val md5: String
    val rating: Rating
    val source: String
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
): DanbooruPost

data class XmlDanbooruPost(
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
): DanbooruPost
