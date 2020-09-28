package post.deserialize

import post.Post

interface PostDeserialize<out P : Post> {
    val post: P
}