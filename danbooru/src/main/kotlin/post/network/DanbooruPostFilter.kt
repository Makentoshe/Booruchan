package post.network

import post.PostId

// TODO should be finished
// https://danbooru.donmai.us/wiki_pages/help:api
sealed class DanbooruPostFilter {

    data class ById(val postId: PostId) : DanbooruPostFilter()
}