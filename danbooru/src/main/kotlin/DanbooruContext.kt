import comment.context.JsonDanbooruCommentContext
import comment.context.JsonDanbooruCommentsContext
import comment.context.XmlDanbooruCommentContext
import comment.context.XmlDanbooruCommentsContext
import comment.network.CommentRequest
import comment.network.CommentsRequest
import context.BooruContext
import post.context.JsonDanbooruPostContext
import post.context.JsonDanbooruPostsContext
import post.context.XmlDanbooruPostContext
import post.context.XmlDanbooruPostsContext
import post.network.PostRequest
import post.network.PostsRequest
import tag.context.JsonDanbooruTagContext
import tag.context.JsonDanbooruTagsContext
import tag.context.XmlDanbooruTagContext
import tag.context.XmlDanbooruTagsContext
import tag.network.TagRequest
import tag.network.TagsRequest

abstract class DanbooruContext : BooruContext {
    override val title: String = "Danbooru"
}

class XmlDanbooruContext : DanbooruContext() {

    override fun post(networkLambda: suspend (PostRequest) -> Result<String>) =
        XmlDanbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (PostsRequest) -> Result<String>) =
        XmlDanbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (TagRequest) -> Result<String>) =
        XmlDanbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (TagsRequest) -> Result<String>) =
        XmlDanbooruTagsContext(networkLambda)

    override fun comment(networkLambda: suspend (CommentRequest) -> Result<String>) =
        XmlDanbooruCommentContext(networkLambda)

    override fun comments(networkLambda: suspend (CommentsRequest) -> Result<String>) =
        XmlDanbooruCommentsContext(networkLambda)
}

class JsonDanbooruContext : DanbooruContext() {

    override fun post(networkLambda: suspend (PostRequest) -> Result<String>) =
        JsonDanbooruPostContext(networkLambda)

    override fun posts(networkLambda: suspend (PostsRequest) -> Result<String>) =
        JsonDanbooruPostsContext(networkLambda)

    override fun tag(networkLambda: suspend (TagRequest) -> Result<String>) =
        JsonDanbooruTagContext(networkLambda)

    override fun tags(networkLambda: suspend (TagsRequest) -> Result<String>) =
        JsonDanbooruTagsContext(networkLambda)

    override fun comment(networkLambda: suspend (CommentRequest) -> Result<String>) =
        JsonDanbooruCommentContext(networkLambda)

    override fun comments(networkLambda: suspend (CommentsRequest) -> Result<String>) =
        JsonDanbooruCommentsContext(networkLambda)
}
