package post

interface GelbooruPosts<out P: GelbooruPost> {
    val posts: List<P>
}

data class XmlGelbooruPosts(val count: Int, val offset: Int, override val posts: List<XmlGelbooruPost>):
    GelbooruPosts<XmlGelbooruPost>

data class JsonGelbooruPosts(override val posts: List<JsonGelbooruPost>): GelbooruPosts<JsonGelbooruPost>

