package post

import Time

interface Post : PostId {
    val creationTime: Time

    val score: Score
    val rating: Rating
    val tags: Tags

    val fullImage: FullImage
    val sampleImage: SampleImage
    val previewImage: PreviewImage

    /** Nullable because if source can be not defined by default */
    val source: String?
}
