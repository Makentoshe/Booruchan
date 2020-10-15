import comment.context.GelbooruCommentsContext
import comment.context.XmlGelbooruCommentsContext
import comment.network.GelbooruCommentsRequest
import post.context.*
import post.network.GelbooruPostRequest
import post.network.GelbooruPostsRequest
import tag.context.*
import tag.network.GelbooruTagRequest
import tag.network.GelbooruTagsRequest

abstract class GelbooruContext {

    val title: String = "Gelbooru"

    abstract fun post(networkLambda: suspend (GelbooruPostRequest) -> Result<String>): GelbooruPostContext<*>

    abstract fun posts(networkLambda: suspend (GelbooruPostsRequest) -> Result<String>): GelbooruPostsContext<*>

    abstract fun tag(networkLambda: suspend (GelbooruTagRequest) -> Result<String>): GelbooruTagContext<*>

    abstract fun tags(networkLambda: suspend (GelbooruTagsRequest) -> Result<String>): GelbooruTagsContext<*>

    abstract fun comments(networkLambda: suspend (GelbooruCommentsRequest) -> Result<String>): GelbooruCommentsContext<*>?
}

class XmlGelbooruContext : GelbooruContext() {

    override fun post(networkLambda: suspend (GelbooruPostRequest) -> Result<String>) =
        XmlGelbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (GelbooruPostsRequest) -> Result<String>) =
        XmlGelbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (GelbooruTagRequest) -> Result<String>) =
        XmlGelbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (GelbooruTagsRequest) -> Result<String>) =
        XmlGelbooruTagsContext(networkLambda)

    override fun comments(networkLambda: suspend (GelbooruCommentsRequest) -> Result<String>) =
        XmlGelbooruCommentsContext(networkLambda)
}

class JsonGelbooruContext : GelbooruContext() {

    override fun post(networkLambda: suspend (GelbooruPostRequest) -> Result<String>) =
        JsonGelbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (GelbooruPostsRequest) -> Result<String>) =
        JsonGelbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (GelbooruTagRequest) -> Result<String>) =
        JsonGelbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (GelbooruTagsRequest) -> Result<String>) =
        JsonGelbooruTagsContext(networkLambda)

    override fun comments(networkLambda: suspend (GelbooruCommentsRequest) -> Result<String>) = null
}
