package com.makentoshe.booruchan.feature.mapper

import com.makentoshe.booruchan.extension.base.entity.NetworkPost
import com.makentoshe.booruchan.feature.entity.Post
import javax.inject.Inject

class NetworkPost2PostMapper @Inject constructor() {
    fun map(networkPost: NetworkPost) = Post(
        id = networkPost.id.toString(),
        previewImageUrl = networkPost.previewImageUrl,
        previewImageHeight = networkPost.previewImageHeight,
        previewImageWidth = networkPost.previewImageWidth,
    )
}