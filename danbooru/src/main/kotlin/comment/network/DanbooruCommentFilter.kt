package comment.network

import comment.CommentId

// TODO should be finished
// https://danbooru.donmai.us/wiki_pages/help:api
sealed class DanbooruCommentFilter {

    data class ById(val commentId: CommentId) : DanbooruCommentFilter()
}
