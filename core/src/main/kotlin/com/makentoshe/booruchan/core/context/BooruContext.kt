package com.makentoshe.booruchan.core.context

import com.makentoshe.booruchan.core.comment.context.CommentContext
import com.makentoshe.booruchan.core.comment.context.CommentsContext
import com.makentoshe.booruchan.core.comment.network.CommentRequest
import com.makentoshe.booruchan.core.comment.network.CommentsRequest
import com.makentoshe.booruchan.core.post.context.PostContext
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.network.PostRequest
import com.makentoshe.booruchan.core.post.network.PostsRequest
import com.makentoshe.booruchan.core.tag.context.TagContext
import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.core.tag.network.TagRequest
import com.makentoshe.booruchan.core.tag.network.TagsRequest

interface BooruContext {

    val title: String

    fun post(networkLambda: suspend (PostRequest) -> Result<String>): PostContext<*, *>?

    fun posts(networkLambda: suspend (PostsRequest) -> Result<String>): PostsContext<*, *>?

    fun tag(networkLambda: suspend (TagRequest) -> Result<String>): TagContext<*, *>?

    fun tags(networkLambda: suspend (TagsRequest) -> Result<String>): TagsContext<*, *>?

    fun comment(networkLambda: suspend (CommentRequest) -> Result<String>): CommentContext<*, *>?

    fun comments(networkLambda: suspend (CommentsRequest) -> Result<String>): CommentsContext<*, *>?
}