package post.deserialize

import post.DanbooruPost

data class DanbooruPostDeserialize<out Post: DanbooruPost>(
    override val post: Post
): PostDeserialize<Post>
