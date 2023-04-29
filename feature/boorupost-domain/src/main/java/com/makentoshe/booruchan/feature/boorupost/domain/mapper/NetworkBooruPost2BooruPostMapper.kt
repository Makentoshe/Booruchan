package com.makentoshe.booruchan.feature.boorupost.domain.mapper

import com.makentoshe.booruchan.feature.boorupost.domain.entity.NetworkBooruPost
import com.makentoshe.booruchan.feature.post.BooruPost
import com.makentoshe.booruchan.feature.post.BooruPostId
import com.makentoshe.booruchan.feature.post.BooruPostImagePreview
import javax.inject.Inject

class NetworkBooruPost2BooruPostMapper @Inject constructor() {

    fun map(networkBooruPost: NetworkBooruPost) = BooruPost(
        id = BooruPostId(networkBooruPost.id),
        preview = BooruPostImagePreview(networkBooruPost.previewImageUrl)
    )
}