package com.makentoshe.booruchan.danbooru

import com.makentoshe.booruchan.danbooru.comment.context.JsonDanbooruCommentContext
import com.makentoshe.booruchan.danbooru.comment.context.JsonDanbooruCommentsContext
import com.makentoshe.booruchan.danbooru.comment.context.XmlDanbooruCommentContext
import com.makentoshe.booruchan.danbooru.comment.context.XmlDanbooruCommentsContext
import com.makentoshe.booruchan.core.comment.network.CommentRequest
import com.makentoshe.booruchan.core.comment.network.CommentsRequest
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.danbooru.post.context.JsonDanbooruPostContext
import com.makentoshe.booruchan.danbooru.post.context.JsonDanbooruPostsContext
import com.makentoshe.booruchan.danbooru.post.context.XmlDanbooruPostContext
import com.makentoshe.booruchan.danbooru.post.context.XmlDanbooruPostsContext
import com.makentoshe.booruchan.core.post.network.PostRequest
import com.makentoshe.booruchan.core.post.network.PostsRequest
import com.makentoshe.booruchan.danbooru.tag.context.JsonDanbooruTagContext
import com.makentoshe.booruchan.danbooru.tag.context.JsonDanbooruTagsContext
import com.makentoshe.booruchan.danbooru.tag.context.XmlDanbooruTagContext
import com.makentoshe.booruchan.danbooru.tag.context.XmlDanbooruTagsContext
import com.makentoshe.booruchan.core.tag.network.TagRequest
import com.makentoshe.booruchan.core.tag.network.TagsRequest

abstract class DanbooruContext : BooruContext {
    override val title: String = "Danbooru"

    override val url: String = object: com.makentoshe.booruchan.danbooru.network.DanbooruRequest() {
        override val url = ""
    }.host
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
