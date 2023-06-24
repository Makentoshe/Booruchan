package com.makentoshe.booruchan.gelbooru

import com.makentoshe.booruchan.core.comment.context.CommentContext
import com.makentoshe.booruchan.core.comment.context.CommentsContext
import com.makentoshe.booruchan.core.comment.network.CommentRequest
import com.makentoshe.booruchan.core.comment.network.CommentsRequest
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.network.PostRequest
import com.makentoshe.booruchan.core.post.network.PostsRequest
import com.makentoshe.booruchan.core.tag.network.TagRequest
import com.makentoshe.booruchan.core.tag.network.TagsRequest
import com.makentoshe.booruchan.gelbooru.comment.context.XmlGelbooruCommentsContext
import com.makentoshe.booruchan.gelbooru.post.context.JsonGelbooruPostContext
import com.makentoshe.booruchan.gelbooru.post.context.JsonGelbooruPostsContext
import com.makentoshe.booruchan.gelbooru.post.context.XmlGelbooruPostContext
import com.makentoshe.booruchan.gelbooru.post.context.XmlGelbooruPostsContext
import com.makentoshe.booruchan.gelbooru.tag.context.JsonGelbooruTagContext
import com.makentoshe.booruchan.gelbooru.tag.context.JsonGelbooruTagsContext
import com.makentoshe.booruchan.gelbooru.tag.context.XmlGelbooruTagContext
import com.makentoshe.booruchan.gelbooru.tag.context.XmlGelbooruTagsContext

abstract class GelbooruContext : BooruContext {
    override val title: String = "Gelbooru"

    override val url: String = object : com.makentoshe.booruchan.gelbooru.network.GelbooruRequest() {
        override val url = ""
    }.host
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
