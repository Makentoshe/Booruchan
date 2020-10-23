package context

import comment.context.CommentContext
import comment.context.CommentsContext
import comment.network.CommentRequest
import comment.network.CommentsRequest
import post.context.PostContext
import post.context.PostsContext
import post.network.PostRequest
import post.network.PostsRequest
import tag.context.TagContext
import tag.context.TagsContext
import tag.network.TagRequest
import tag.network.TagsRequest

interface BooruContext {

    val title: String

    val url: String

    fun post(networkLambda: suspend (PostRequest) -> Result<String>): PostContext<*, *>?

    fun posts(networkLambda: suspend (PostsRequest) -> Result<String>): PostsContext<*, *>?

    fun tag(networkLambda: suspend (TagRequest) -> Result<String>): TagContext<*, *>?

    fun tags(networkLambda: suspend (TagsRequest) -> Result<String>): TagsContext<*, *>?

    fun comment(networkLambda: suspend (CommentRequest) -> Result<String>): CommentContext<*, *>?

    fun comments(networkLambda: suspend (CommentsRequest) -> Result<String>): CommentsContext<*, *>?
}