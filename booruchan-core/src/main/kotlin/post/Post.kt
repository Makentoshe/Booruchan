package post

import CreationTime
import Md5
import Score

interface Post: PostId, Rating, Source, Md5, Score {
    val raw: Map<String, String>
    val previewImage: PreviewImage
    val sampleImage: SampleImage
    val fullImage: FullImage
    val creationTime: CreationTime
    val tags: Tags
}