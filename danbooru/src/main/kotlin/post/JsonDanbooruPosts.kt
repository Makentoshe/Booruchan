package post

interface DanbooruPosts<out P: DanbooruPost> {
    val posts: List<P>
}

data class XmlDanbooruPosts(override val posts: List<XmlDanbooruPost>): DanbooruPosts<XmlDanbooruPost>

data class JsonDanbooruPosts(override val posts: List<JsonDanbooruPost>): DanbooruPosts<JsonDanbooruPost>

