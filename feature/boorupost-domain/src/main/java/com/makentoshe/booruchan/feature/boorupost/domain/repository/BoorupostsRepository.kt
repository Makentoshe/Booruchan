package com.makentoshe.booruchan.feature.boorupost.domain.repository

import com.makentoshe.booruchan.feature.BooruSystem
import com.makentoshe.booruchan.feature.boorupost.domain.repository.request.BoorupostsRequest
import com.makentoshe.booruchan.feature.boorupost.domain.repository.response.BoorupostsResponse

interface BoorupostsRepository {

    val supportedBooruSystem: BooruSystem

    suspend fun getPosts(request: BoorupostsRequest): BoorupostsResponse
}
