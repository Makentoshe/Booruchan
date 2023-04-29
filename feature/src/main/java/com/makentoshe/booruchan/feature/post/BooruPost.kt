package com.makentoshe.booruchan.feature.post

@JvmInline
value class BooruPostId(val int: Int)

@JvmInline
value class BooruPostImagePreview(val url: String)


data class BooruPost(
    val id: BooruPostId,
    val preview: BooruPostImagePreview,
)
