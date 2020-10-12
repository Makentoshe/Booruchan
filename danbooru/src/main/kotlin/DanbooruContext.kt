import comment.context.*
import comment.network.DanbooruCommentRequest
import comment.network.DanbooruCommentsRequest
import post.context.*
import post.network.DanbooruPostRequest
import post.network.DanbooruPostsRequest
import tag.context.*
import tag.network.DanbooruTagRequest
import tag.network.DanbooruTagsRequest

abstract class DanbooruContext {

    val title: String = "Danbooru"

    abstract fun post(networkLambda: suspend (DanbooruPostRequest) -> Result<String>): DanbooruPostContext<*>

    abstract fun posts(networkLambda: suspend (DanbooruPostsRequest) -> Result<String>): DanbooruPostsContext<*>

    abstract fun tag(networkLambda: suspend (DanbooruTagRequest) -> Result<String>): DanbooruTagContext<*>

    abstract fun tags(networkLambda: suspend (DanbooruTagsRequest) -> Result<String>): DanbooruTagsContext<*>

    abstract fun comment(networkLambda: suspend (DanbooruCommentRequest) -> Result<String>): DanbooruCommentContext<*>

    abstract fun comments(networkLambda: suspend (DanbooruCommentsRequest) -> Result<String>): DanbooruCommentsContext<*>
}

class XmlDanbooruContext : DanbooruContext() {

    override fun post(networkLambda: suspend (DanbooruPostRequest) -> Result<String>) =
        XmlDanbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (DanbooruPostsRequest) -> Result<String>) =
        XmlDanbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (DanbooruTagRequest) -> Result<String>) =
        XmlDanbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (DanbooruTagsRequest) -> Result<String>) =
        XmlDanbooruTagsContext(networkLambda)

    override fun comment(networkLambda: suspend (DanbooruCommentRequest) -> Result<String>) =
        XmlDanbooruCommentContext(networkLambda)

    override fun comments(networkLambda: suspend (DanbooruCommentsRequest) -> Result<String>) =
        XmlDanbooruCommentsContext(networkLambda)
}

class JsonDanbooruContext : DanbooruContext() {

    override fun post(networkLambda: suspend (DanbooruPostRequest) -> Result<String>) =
        JsonDanbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (DanbooruPostsRequest) -> Result<String>) =
        JsonDanbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (DanbooruTagRequest) -> Result<String>) =
        JsonDanbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (DanbooruTagsRequest) -> Result<String>) =
        JsonDanbooruTagsContext(networkLambda)

    override fun comment(networkLambda: suspend (DanbooruCommentRequest) -> Result<String>) =
        JsonDanbooruCommentContext(networkLambda)

    override fun comments(networkLambda: suspend (DanbooruCommentsRequest) -> Result<String>) =
        JsonDanbooruCommentsContext(networkLambda)
}
