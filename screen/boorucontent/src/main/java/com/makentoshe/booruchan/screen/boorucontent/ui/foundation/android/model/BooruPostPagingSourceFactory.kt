package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model

import com.makentoshe.booruchan.feature.BooruContext
import com.makentoshe.booruchan.feature.boorupost.domain.usecase.FetchBooruPostsUseCase
import com.makentoshe.booruchan.screen.boorucontent.mapper.BooruPost2BooruPreviewPostUiMapper
import javax.inject.Inject

class BooruPostPagingSourceFactory @Inject constructor(
    private val fetchBooruPostsUseCase: FetchBooruPostsUseCase,
    private val booruPost2BooruPreviewPostUiMapper: BooruPost2BooruPreviewPostUiMapper,
) {
    fun build(booruContext: BooruContext) = BooruPostPagingSource(
        fetchBooruPosts = fetchBooruPostsUseCase,
        mapper = booruPost2BooruPreviewPostUiMapper,
        booruContext = booruContext
    )
}