package post

import Time
import Hash
import Score

interface Post : PostId, Hash, Rating, Score, Source, HasComments, HasChildren {
    val raw: Map<String, String>
    val previewImage: PreviewImage
    val sampleImage: SampleImage
    val fullImage: FullImage
    val creationTime: Time
    val tags: Tags
}