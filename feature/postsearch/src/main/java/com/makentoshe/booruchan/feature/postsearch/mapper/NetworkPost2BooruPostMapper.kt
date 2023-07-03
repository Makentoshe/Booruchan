package com.makentoshe.booruchan.feature.postsearch.mapper

import com.makentoshe.booruchan.extension.entity.NetworkPost
import com.makentoshe.booruchan.feature.post.BooruPost
import com.makentoshe.booruchan.feature.post.BooruPostId
import com.makentoshe.booruchan.feature.post.BooruPostImage
import com.makentoshe.booruchan.feature.post.BooruPostImageSize
import javax.inject.Inject

class NetworkPost2BooruPostMapper @Inject constructor() {

    fun map(networkBooruPost: NetworkPost) = BooruPost(
        id = BooruPostId(networkBooruPost.id),
        preview = BooruPostImage(
            url = networkBooruPost.previewImageUrl,
            size = BooruPostImageSize(
                height = networkBooruPost.previewImageHeight,
                width = networkBooruPost.previewImageWidth,
                hwRatio = calculateHwRatio(
                    networkBooruPost.previewImageHeight, networkBooruPost.previewImageWidth,
                )
            )
        )
    )

    private fun calculateHwRatio(height: Int, width: Int): Float {
        if (height == 0 || width == 0) return 1.0f

        return height.toFloat() / width.toFloat()
    }
}