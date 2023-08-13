package com.makentoshe.booruchan.feature.fetchposts

import com.makentoshe.booruchan.extension.base.factory.FetchPostsFactory
import com.makentoshe.booruchan.feature.entity.Post
import com.makentoshe.booruchan.feature.mapper.NetworkPost2PostMapper
import com.makentoshe.booruchan.feature.network.NetworkRepository
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val networkPost2PostMapper: NetworkPost2PostMapper,
) {
    suspend operator fun invoke(
        fetchPostsFactory: FetchPostsFactory,
        request: FetchPostsFactory.FetchPostsRequest,
    ): List<Post> {
        val networkRequest = fetchPostsFactory.buildRequest(request)
        val networkResponse = networkRepository.execute(networkRequest)
        val networkPosts = fetchPostsFactory.parseResponse(networkResponse)
        return networkPosts.map(networkPost2PostMapper::map)
    }
}
