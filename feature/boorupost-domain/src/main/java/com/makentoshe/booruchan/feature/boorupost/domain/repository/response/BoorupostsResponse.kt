package com.makentoshe.booruchan.feature.boorupost.domain.repository.response

import com.makentoshe.booruchan.feature.boorupost.domain.entity.NetworkBooruPost
import com.makentoshe.booruchan.feature.boorupost.domain.repository.request.BoorupostsRequest

data class BoorupostsResponse(
    val request: BoorupostsRequest,
    val booruPosts: List<NetworkBooruPost>,
)
