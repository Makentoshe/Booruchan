package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model

import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.feature.postsearch.FetchPostsUseCase
import com.makentoshe.booruchan.feature.search.BooruSearch
import com.makentoshe.booruchan.screen.boorucontent.mapper.BooruPost2BooruPreviewPostUiMapper
import javax.inject.Inject

class BooruPostPagingSourceFactory @Inject constructor(
    private val fetchPosts: FetchPostsUseCase,
    private val booruPost2BooruPreviewPostUiMapper: BooruPost2BooruPreviewPostUiMapper,
) {
    fun build(postSearchFactory: BooruPostSearchFactory, booruSearch: BooruSearch) = BooruPostPagingSource(
        mapper = booruPost2BooruPreviewPostUiMapper,
        postSearchFactory = postSearchFactory,
        booruSearch = booruSearch,
        fetchPosts = fetchPosts
    )
}