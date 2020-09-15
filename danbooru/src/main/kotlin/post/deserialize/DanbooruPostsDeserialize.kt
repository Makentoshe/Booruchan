package post.deserialize

import post.DanbooruPost

abstract class DanbooruPostsDeserialize {

    abstract val deserializes: List<DanbooruPostDeserialize>

    abstract val posts: List<DanbooruPostDeserialize.Success<DanbooruPost>>

    abstract val failures: List<DanbooruPostDeserialize.Failure>
}

class XmlDanbooruPostsDeserialize(
    override val deserializes: List<XmlDanbooruPostDeserialize>
) : DanbooruPostsDeserialize() {
    override val posts = deserializes.filterIsInstance<XmlDanbooruPostDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<XmlDanbooruPostDeserialize.Failure>()
}


class JsonDanbooruPostsDeserialize(
    override val deserializes: List<JsonDanbooruPostDeserialize>
) : DanbooruPostsDeserialize() {
    override val posts = deserializes.filterIsInstance<JsonDanbooruPostDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<JsonDanbooruPostDeserialize.Failure>()
}

