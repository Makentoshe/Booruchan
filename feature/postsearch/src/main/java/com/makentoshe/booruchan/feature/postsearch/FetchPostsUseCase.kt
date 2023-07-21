package com.makentoshe.booruchan.feature.postsearch

import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.feature.NetworkRepository
import com.makentoshe.booruchan.feature.post.BooruPost
import com.makentoshe.booruchan.feature.postsearch.mapper.NetworkPost2BooruPostMapper
import com.makentoshe.booruchan.feature.text
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val mapper: NetworkPost2BooruPostMapper,
){

    suspend operator fun invoke(request: BooruPostSearchFactory.FetchPostsRequest, postSearchFactory: BooruPostSearchFactory): List<BooruPost> {
        val networkRequest = postSearchFactory.buildRequest(request)
        val networkPosts = postSearchFactory.parseResponse(networkRepository.execute(networkRequest))
        return networkPosts.map(mapper::map)
    }
}