import post.*

// todo was not completed
data class GelbooruPost(
    override val raw: Map<String, String>,
    override val postId: Int,
    override val score: Int,
    override val md5: String,
    override val rating: String,
    override val source: String,
    override val hasComments: Boolean,
    override val hasChildren: Boolean,
    override val creationTime: CreationTime,
    override val fullImage: FullImage,
    override val previewImage: PreviewImage,
    override val sampleImage: SampleImage,
    override val tags: Tags,
    val change: String
) : Post, HasComments, HasChildren

