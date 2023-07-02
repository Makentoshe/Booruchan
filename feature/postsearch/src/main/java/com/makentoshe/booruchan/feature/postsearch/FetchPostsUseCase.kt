package com.makentoshe.booruchan.feature.postsearch

import com.makentoshe.booruchan.feature.NetworkRepository
import com.makentoshe.booruchan.feature.NetworkRequest
import com.makentoshe.booruchan.feature.post.BooruPost
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
){

    suspend operator fun invoke(networkRequest: NetworkRequest) {
        val networkResponse = networkRepository.execute(networkRequest)


    }
}