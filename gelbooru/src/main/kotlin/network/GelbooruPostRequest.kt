package network

import post.PostId

sealed class GelbooruPostRequest {

    abstract val url: String

    data class Xml(val postId: PostId) : GelbooruPostRequest() {
        override val url = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&id=${postId.postId}"
    }

    data class Json(val postId: PostId) : GelbooruPostRequest() {
        override val url = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&json=1&id=${postId.postId}"
    }
}