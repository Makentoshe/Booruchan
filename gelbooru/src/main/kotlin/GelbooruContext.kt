import comment.context.CommentContext
import comment.context.CommentsContext
import comment.context.XmlGelbooruCommentsContext
import comment.network.CommentRequest
import comment.network.CommentsRequest
import context.BooruContext
import post.context.JsonGelbooruPostContext
import post.context.JsonGelbooruPostsContext
import post.context.XmlGelbooruPostContext
import post.context.XmlGelbooruPostsContext
import post.network.PostRequest
import post.network.PostsRequest
import tag.context.JsonGelbooruTagContext
import tag.context.JsonGelbooruTagsContext
import tag.context.XmlGelbooruTagContext
import tag.context.XmlGelbooruTagsContext
import tag.network.TagRequest
import tag.network.TagsRequest

abstract class GelbooruContext : BooruContext {
    override val title: String = "Gelbooru"
}

class XmlGelbooruContext : GelbooruContext() {

    override fun post(networkLambda: suspend (PostRequest) -> Result<String>) =
        XmlGelbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (PostsRequest) -> Result<String>) =
        XmlGelbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (TagRequest) -> Result<String>) =
        XmlGelbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (TagsRequest) -> Result<String>) =
        XmlGelbooruTagsContext(networkLambda)

    override fun comment(networkLambda: suspend (CommentRequest) -> Result<String>): CommentContext<*, *>? =
        null

    override fun comments(networkLambda: suspend (CommentsRequest) -> Result<String>) =
        XmlGelbooruCommentsContext(networkLambda)
}

class JsonGelbooruContext : GelbooruContext() {

    override fun post(networkLambda: suspend (PostRequest) -> Result<String>) =
        JsonGelbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (PostsRequest) -> Result<String>) =
        JsonGelbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (TagRequest) -> Result<String>) =
        JsonGelbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (TagsRequest) -> Result<String>) =
        JsonGelbooruTagsContext(networkLambda)

    override fun comment(networkLambda: suspend (CommentRequest) -> Result<String>): CommentContext<*, *>? =
        null

    override fun comments(networkLambda: suspend (CommentsRequest) -> Result<String>): CommentsContext<*, *>? =
        null
}
