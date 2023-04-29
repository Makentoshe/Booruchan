package com.makentoshe.booruchan.feature.post

@JvmInline
value class BooruPostId(val int: Int)

data class BooruPostImage(val url: String, val size: BooruPostImageSize)

data class BooruPostImageSize(
    val height: Int,
    val width: Int,
    val hwRatio: Float,
)

data class BooruPost(
    val id: BooruPostId,
    val preview: BooruPostImage,
)
