package post.network

import post.PostId

sealed class DanbooruPostRequest {

    abstract val url: String

    data class Xml(val postId: PostId) : DanbooruPostRequest() {
        override val url = "https://danbooru.donmai.us/posts/${postId.postId}.xml"
    }

    data class Json(val postId: PostId) : DanbooruPostRequest() {
        override val url = "https://danbooru.donmai.us/posts/${postId.postId}.json"
    }
}


