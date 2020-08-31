package post

import Time
import Hash
import Score

interface Post : PostId {
    val raw: Map<String, String>
    val previewImage: PreviewImage
    val sampleImage: SampleImage
    val fullImage: FullImage
    val creationTime: Time
    val tags: Tags
    val md5: String
    val source: String
    val score: Int
    val rating: Rating
    val hasComments: Boolean
}