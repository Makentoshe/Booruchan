package post

import Time

interface GelbooruPost : PostId {
    val score: Int
    val md5: String
    val rating: Rating
    val source: String
    val creationTime: Time
    val tags: Tags
    val fullImage: FullImage
    val sampleImage: SampleImage
    val previewImage: PreviewImage
}

data class XmlGelbooruPost(
    override val postId: Int,
    override val score: Int,
    override val md5: String,
    override val rating: Rating,
    override val source: String,
    override val creationTime: Time,
    override val fullImage: FullImage,
    override val previewImage: PreviewImage,
    override val sampleImage: SampleImage,
    override val tags: Tags,
    val hasComments: Boolean,
    val change: String
) : GelbooruPost

data class JsonGelbooruPost(
    override val postId: Int,
    override val score: Int,
    override val md5: String,
    override val rating: Rating,
    override val source: String,
    override val creationTime: Time,
    override val fullImage: FullImage,
    override val sampleImage: SampleImage,
    override val previewImage: PreviewImage,
    override val tags: Tags,
    val owner: String,
    val parentId: Int?,
    val change: Int
) : GelbooruPost
