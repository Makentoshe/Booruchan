package post.deserialize

import post.GelbooruPost

interface GelbooruPostsDeserialize {

    val deserializes: List<GelbooruPostDeserialize>

    val posts: List<GelbooruPostDeserialize.Success<GelbooruPost>>

    val failures: List<GelbooruPostDeserialize.Failure>
}

class XmlGelbooruPostsDeserialize(
    override val deserializes: List<XmlGelbooruPostDeserialize>
) : GelbooruPostsDeserialize {
    override val posts = deserializes.filterIsInstance<XmlGelbooruPostDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<XmlGelbooruPostDeserialize.Failure>()
}


class JsonGelbooruPostsDeserialize(
    override val deserializes: List<JsonGelbooruPostDeserialize>
) : GelbooruPostsDeserialize {
    override val posts = deserializes.filterIsInstance<JsonGelbooruPostDeserialize.Success>()
    override val failures = deserializes.filterIsInstance<JsonGelbooruPostDeserialize.Failure>()
}

