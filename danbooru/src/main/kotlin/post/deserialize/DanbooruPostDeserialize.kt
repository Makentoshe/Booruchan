package post.deserialize

import post.DanbooruPost

data class DanbooruPostDeserialize<out Post: DanbooruPost>(val post: Post)
