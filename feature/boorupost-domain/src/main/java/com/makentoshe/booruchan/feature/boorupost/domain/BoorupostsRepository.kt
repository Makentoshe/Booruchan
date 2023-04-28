package com.makentoshe.booruchan.feature.boorupost.domain

import com.makentoshe.booruchan.feature.BooruSystem

interface BoorupostsRepository {

    val supportedBooruSystem: BooruSystem

    suspend fun getPosts(request: BoorupostsRequest): BoorupostsResponse
}

data class BoorupostsRepositories(val list: List<BoorupostsRepository>)