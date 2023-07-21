package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.android.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.feature.postsearch.FetchPostsUseCase
import com.makentoshe.booruchan.feature.search.BooruSearch
import com.makentoshe.booruchan.screen.boorucontent.domain.BooruPreviewPostUi
import com.makentoshe.booruchan.screen.boorucontent.mapper.BooruPost2BooruPreviewPostUiMapper
import javax.inject.Inject

/**
 * @param fetchPosts usecase that consumes network request from [postSearchFactory] and returns a list of posts
 * @param postSearchFactory factory creates network request based on [booruSearch]
 * @param booruSearch info about current search query
 * */
class BooruPostPagingSource @Inject constructor(
    private val fetchPosts: FetchPostsUseCase,
    private val postSearchFactory: BooruPostSearchFactory,
    private val booruSearch: BooruSearch,
    private val mapper: BooruPost2BooruPreviewPostUiMapper,
) : PagingSource<Int, BooruPreviewPostUi>() {

    override fun getRefreshKey(state: PagingState<Int, BooruPreviewPostUi>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BooruPreviewPostUi> {
        return try {
            internalLoad(params)
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)
        }
    }

    private suspend fun internalLoad(params: LoadParams<Int>): LoadResult<Int, BooruPreviewPostUi> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: postSearchFactory.initialPageNumber
        val postsPerPage = params.loadSize // booruContext.settings.searchSettings.requestedPostsPerPageCount
        val tagSeparator = postSearchFactory.searchTagSeparator
        val tags = booruSearch.tags.joinToString(separator = tagSeparator) { it.string }

        val fetchPostsRequest = BooruPostSearchFactory.FetchPostsRequest(postsPerPage, nextPageNumber, tags)
        val response = fetchPosts(fetchPostsRequest, postSearchFactory)

        return LoadResult.Page(
            data = response.map(mapper::map),
            prevKey = null, // Only paging forward.
            nextKey = nextPageNumber + 1
        )
    }
}
